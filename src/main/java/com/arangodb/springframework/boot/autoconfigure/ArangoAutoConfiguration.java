/*
 * DISCLAIMER
 *
 * Copyright 2018 ArangoDB GmbH, Cologne, Germany
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
package com.arangodb.springframework.boot.autoconfigure;

import com.arangodb.ArangoDB;
import com.arangodb.ContentType;
import com.arangodb.config.HostDescription;
import com.arangodb.internal.serde.ContentTypeFactory;
import com.arangodb.springframework.config.ArangoConfiguration;
import com.arangodb.springframework.core.ArangoOperations;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * {@link EnableAutoConfiguration} class for ArangoDB
 *
 * @author Mark Vollmary
 * @author Arne Burmeister
 */
@AutoConfiguration
@ConditionalOnClass(ArangoDB.class)
@ConditionalOnMissingBean(ArangoOperations.class)
@EnableConfigurationProperties(ArangoProperties.class)
@Import({ArangoRepositoriesAutoConfigureRegistrar.class, ArangoAutoConfiguration.ArangoBootConfiguration.class})
public class ArangoAutoConfiguration {

    @Configuration
    static class ArangoBootConfiguration implements ArangoConfiguration {

        private final ArangoProperties properties;

        ArangoBootConfiguration(final ArangoProperties properties) {
            super();
            this.properties = properties;
        }

        @Override
        public ArangoDB.Builder arango() {
            final ArangoDB.Builder builder = new ArangoDB.Builder()
                    .user(properties.getUser())
                    .password(properties.getPassword())
                    .jwt(properties.getJwt())
                    .timeout(properties.getTimeout())
                    .useSsl(properties.getUseSsl())
                    .maxConnections(properties.getMaxConnections())
                    .connectionTtl(properties.getConnectionTtl())
                    .acquireHostList(properties.getAcquireHostList())
                    .acquireHostListInterval(properties.getAcquireHostListInterval())
                    .loadBalancingStrategy(properties.getLoadBalancingStrategy())
                    .protocol(properties.getProtocol());
            properties.getHosts().stream().map(HostDescription::parse)
                    .forEach(host -> builder.host(host.getHost(), host.getPort()));
            return builder;
        }

        @Override
        public ContentType contentType() {
            return ContentTypeFactory.of(properties.getProtocol());
        }

        @Override
        public String database() {
        return properties.getDatabase();
    }
    }
}
