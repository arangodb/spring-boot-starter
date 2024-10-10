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

package com.arangodb.spring.demo.runner;

import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.entity.ChildOf;
import com.arangodb.spring.demo.repository.CharacterRepository;
import com.arangodb.spring.demo.repository.ChildOfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mark Vollmary
 */
@Order(4)
@ComponentScan("com.arangodb.spring.demo")
public class RelationsRunner implements CommandLineRunner {

    @Autowired
    private CharacterRepository characterRepo;
    @Autowired
    private ChildOfRepository childOfRepo;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("# Relations");
        characterRepo.saveAll(CrudRunner.createCharacters());
        characterRepo.save(CrudRunner.createNedStark());

        // first create some relations for the Starks and Lannisters
        Character ned = characterRepo.findByNameAndSurname("Ned", "Stark").get();
        Character catelyn = characterRepo.findByNameAndSurname("Catelyn", "Stark").get();
        Character robb = characterRepo.findByNameAndSurname("Robb", "Stark").get();
        childOfRepo.saveAll(Arrays.asList(new ChildOf(robb, ned), new ChildOf(robb, catelyn)));
        Character sansa = characterRepo.findByNameAndSurname("Sansa", "Stark").get();
        childOfRepo.saveAll(Arrays.asList(new ChildOf(sansa, ned), new ChildOf(sansa, catelyn)));
        Character arya = characterRepo.findByNameAndSurname("Arya", "Stark").get();
        childOfRepo.saveAll(Arrays.asList(new ChildOf(arya, ned), new ChildOf(arya, catelyn)));
        Character bran = characterRepo.findByNameAndSurname("Bran", "Stark").get();
        childOfRepo.saveAll(Arrays.asList(new ChildOf(bran, ned), new ChildOf(bran, catelyn)));
        Character jon = characterRepo.findByNameAndSurname("Jon", "Snow").get();
        childOfRepo.save(new ChildOf(jon, ned));

        Character tywin = characterRepo.findByNameAndSurname("Tywin", "Lannister").get();
        Character jaime = characterRepo.findByNameAndSurname("Jaime", "Lannister").get();
        childOfRepo.save(new ChildOf(jaime, tywin));
        Character cersei = characterRepo.findByNameAndSurname("Cersei", "Lannister").get();
        childOfRepo.save(new ChildOf(cersei, tywin));
        Character joffrey = characterRepo.findByNameAndSurname("Joffrey", "Baratheon").get();
        childOfRepo.saveAll(Arrays.asList(new ChildOf(joffrey, jaime), new ChildOf(joffrey, cersei)));
        Character tyrion = characterRepo.findByNameAndSurname("Tyrion", "Lannister").get();
        childOfRepo.save(new ChildOf(tyrion, tywin));

        Character nedStark = characterRepo.findByNameAndSurname("Ned", "Stark").get();
        System.out.println(String.format("## These are the children of %s:", nedStark));
        nedStark.getChildren().forEach(System.out::println);
        assertThat(nedStark.getChildren()).isNotEmpty();

        System.out.println("## These are the parents of 'Sansa'");
        Iterable<Character> parentsOfSansa = characterRepo.findByChildrenName("Sansa");
        parentsOfSansa.forEach(System.out::println);
        assertThat(parentsOfSansa).isNotEmpty();

        System.out.println("## These parents have a child which is between 16 and 20 years old");
        Iterable<Character> childrenBetween16a20 = characterRepo.findByChildrenAgeBetween(16, 20);
        childrenBetween16a20.forEach(System.out::println);
        assertThat(childrenBetween16a20).isNotEmpty();
    }

}
