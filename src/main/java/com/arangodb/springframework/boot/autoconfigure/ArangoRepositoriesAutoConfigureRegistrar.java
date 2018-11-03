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

import java.lang.annotation.Annotation;

import org.springframework.boot.autoconfigure.data.AbstractRepositoryConfigurationSourceSupport;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.repository.ArangoRepositoryConfigurationExtension;

/**
 * @author Mark Vollmary
 *
 */
public class ArangoRepositoriesAutoConfigureRegistrar extends AbstractRepositoryConfigurationSourceSupport {

	@Override
	protected Class<? extends Annotation> getAnnotation() {
		return EnableArangoRepositories.class;
	}

	@Override
	protected Class<?> getConfiguration() {
		return EnableArangoRepositoriesConfiguration.class;
	}

	@Override
	protected RepositoryConfigurationExtension getRepositoryConfigurationExtension() {
		return new ArangoRepositoryConfigurationExtension();
	}

	@EnableArangoRepositories
	private static class EnableArangoRepositoriesConfiguration {
	}

}
