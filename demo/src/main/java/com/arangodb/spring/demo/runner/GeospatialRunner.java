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

import com.arangodb.spring.demo.entity.Location;
import com.arangodb.spring.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Range;
import org.springframework.data.geo.*;

import java.util.Arrays;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Mark Vollmary
 */
@Order(6)
public class GeospatialRunner implements CommandLineRunner {

    @Autowired
    private LocationRepository repository;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("# Geospatial");

        repository.saveAll(Arrays.asList(
                new Location("Dragonstone",     new Point(-6.815096, 55.167801)),
                new Location("King's Landing",  new Point(18.110189, 42.639752)),
                new Location("The Red Keep",    new Point(14.446442, 35.896447)),
                new Location("Yunkai",          new Point(-7.129532, 31.046642)),
                new Location("Astapor",         new Point(-9.774249, 31.50974)),
                new Location("Winterfell",      new Point(-5.581312, 54.368321)),
                new Location("Vaes Dothrak",    new Point(-6.096125, 54.16776)),
                new Location("Beyond the wall", new Point(-21.094093, 64.265473))
        ));

        System.out.println("## Find the first 5 locations near 'Winterfell'");
        GeoPage<Location> first5 = repository.findByLocationNear(new Point(-5.581312, 54.368321),
                PageRequest.of(0, 5));
        first5.forEach(System.out::println);
        assertThat(first5).hasSize(5);

        System.out.println("## Find the next 5 locations near 'Winterfell' (only 3 locations left)");
        GeoPage<Location> next5 = repository.findByLocationNear(new Point(-5.581312, 54.368321),
                PageRequest.of(1, 5));
        next5.forEach(System.out::println);
        assertThat(next5).hasSize(3);

        assertThat(first5.and(next5).get().toList())
                .isSortedAccordingTo(Comparator.comparing(GeoResult::getDistance));

        System.out.println("## Find all locations within 50 kilometers of 'Winterfell'");
        GeoResults<Location> findWithing50kilometers = repository
                .findByLocationWithin(new Point(-5.581312, 54.368321), new Distance(50, Metrics.KILOMETERS));
        findWithing50kilometers.forEach(System.out::println);
        assertThat(findWithing50kilometers.getContent())
                .isNotEmpty()
                .isSortedAccordingTo(Comparator.comparing(GeoResult::getDistance));


        System.out.println("## Find all locations which are 40 to 50 kilometers away from 'Winterfell'");
        Iterable<Location> findByLocationWithin = repository.findByLocationWithin(new Point(-5.581312, 54.368321),
                Range.of(Range.Bound.inclusive(40000.), Range.Bound.exclusive(50000.)));
        findByLocationWithin.forEach(System.out::println);
        assertThat(findByLocationWithin).isNotEmpty();

        System.out.println("## Find all locations within a given polygon");
        Iterable<Location> withinPolygon = repository.findByLocationWithin(
                new Polygon(Arrays.asList(new Point(-25, 40), new Point(-25, 70), new Point(25, 70), new Point(-25, 40))));
        withinPolygon.forEach(System.out::println);
        assertThat(withinPolygon).isNotEmpty();
    }

}
