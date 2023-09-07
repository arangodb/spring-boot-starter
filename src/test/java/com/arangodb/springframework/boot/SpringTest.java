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
import com.arangodb.springframework.boot.actuate.ArangoHealthIndicator;
import com.arangodb.springframework.core.ArangoOperations;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.CrudRepository;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michele Rastelli
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
}

interface CatRepo extends CrudRepository<Cat, String> {
}


@Document
class Cat {
    @Id
    String id;
    String name;
}
