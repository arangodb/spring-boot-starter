/*
 * DISCLAIMER
 *
 * Copyright 2017 ArangoDB GmbH, Cologne, Germany
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
package com.arangodb.spring.demo.entity;
import com.arangodb.springframework.annotation.ArangoId;
import com.arangodb.springframework.annotation.Document;
import com.arangodb.springframework.annotation.PersistentIndex;
import com.arangodb.springframework.annotation.Relations;
import org.springframework.data.annotation.Id;
import java.util.Collection;
/**
 * @author Mark Vollmary
 */
@Document("characters")
@PersistentIndex(fields = {"surname"})
public record Character(
        @Id String id,
        @ArangoId String arangoId,
        String name,
        String surname,
        boolean alive,
        Integer age,
        @Relations(edges = ChildOf.class, lazy = true) Collection<Character> children
) {
    public Character(final String name, final String surname, final boolean alive) {
        this(null, null, name, surname, alive, null, null);
    }

    public Character(final String name, final String surname, final boolean alive, final Integer age) {
        this(null, null, name, surname, alive, age, null);
    }

    public Character withAlive(final boolean alive) {
        return new Character(this.id, this.arangoId, this.name, this.surname, alive, this.age, this.children);
    }
}
