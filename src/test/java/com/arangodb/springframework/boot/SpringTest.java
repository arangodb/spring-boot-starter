/*
 * DISCLAIMER
 *
 * Copyright 2016 ArangoDB GmbH, Cologne, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Copyright holder is ArangoDB GmbH, Cologne, Germany
 */

package com.arangodb.springframework.boot;

import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.Ref;
import com.arangodb.springframework.boot.actuate.ArangoHealthIndicator;
import com.arangodb.springframework.core.ArangoOperations;
import com.arangodb.springframework.core.mapping.event.AfterLoadEvent;
import com.arangodb.springframework.core.mapping.event.AfterSaveEvent;
import com.arangodb.springframework.core.mapping.event.ArangoMappingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michele Rastelli
 * @author Arne Burmeister
 */
@SpringBootTest
@EnableAutoConfiguration
@SpringBootConfiguration
public class SpringTest {

    @Autowired
    private ArangoOperations operations;

    @Autowired
    private ArangoHealthIndicator arangoHealthIndicator;

    @Autowired
    private CatRepo repo;

    @Autowired
    private MouseRepo referencingRepo;

    @Autowired
    private Listener listener;

    @BeforeEach
    void clearEvents() {
        listener.events.clear();
    }

    @Test
    public void operationsShouldBeNotNull() {
        assertThat(operations).isNotNull();
    }

    @Test
    public void getVersion() {
        assertThat(operations.getVersion()).isNotNull();
    }

    @Test
    public void health() {
        assertThat(arangoHealthIndicator.health().getStatus()).isEqualTo(Status.UP);
    }

    @Test
    void repo() {
        Cat cat = new Cat();
        cat.name = "Silvestro";
        Cat saved = repo.save(cat);
        Cat read = repo.findById(saved.id).orElseThrow();
        assertThat(read.id).isNotNull();
        assertThat(read.name).isEqualTo(cat.name);
    }

    @Test
    void event() {
        Cat cat = new Cat();
        cat.name = "Tom";
        Cat saved = repo.save(cat);
        assertThat(listener.events).hasSize(3)
                .describedAs("event after save of cat")
                .anyMatch(event -> matches(event, AfterSaveEvent.class, saved));
        Mouse mouse = new Mouse();
        mouse.huntBy = cat;
        referencingRepo.save(mouse);
        listener.events.clear();

        Cat hunter = referencingRepo.findById(mouse.id).orElseThrow().huntBy;
        assertThat(hunter).isEqualTo(saved);
        assertThat(listener.events).describedAs("event after resolving cat")
                .anyMatch(event -> matches(event, AfterLoadEvent.class, hunter));
    }

    @TestConfiguration
    static class EventConfig {
        @Bean
        ApplicationListener<ArangoMappingEvent<?>> listener() {
            return new Listener();
        }
    }

    @SuppressWarnings("rawtypes")
    private static boolean matches(ArangoMappingEvent<?> event, Class<? extends ArangoMappingEvent> type, Object source) {
        return type.isInstance(event) && event.getSource().equals(source);
    }
}

interface CatRepo extends CrudRepository<Cat, String> {
}

interface MouseRepo extends CrudRepository<Mouse, String> {
}

@Document
class Cat {
    @Id
    String id;
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id) && Objects.equals(name, cat.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

@Document
class Mouse {
    @Id
    String id;

    @Ref
    Cat huntBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mouse mouse = (Mouse) o;
        return Objects.equals(id, mouse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

class Listener implements ApplicationListener<ArangoMappingEvent<?>> {

    final List<ArangoMappingEvent<?>> events = new ArrayList<>();

    @Override
    public void onApplicationEvent(@NonNull ArangoMappingEvent<?> event) {
        events.add(event);
    }
}
