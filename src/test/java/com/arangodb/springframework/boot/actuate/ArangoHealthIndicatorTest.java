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
package com.arangodb.springframework.boot.actuate;

import com.arangodb.ArangoDB;
import com.arangodb.entity.ArangoDBVersion;
import com.arangodb.entity.License;
import com.arangodb.springframework.core.ArangoOperations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Status;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * @author Mark Vollmary
 *
 */
public class ArangoHealthIndicatorTest {

	@Test
	public void doHealthCheck() {
		final ArangoOperations operations = mock(ArangoOperations.class);
		{
			final ArangoDB arango = mock(ArangoDB.class);
			{
				final ArangoDBVersion version = mock(ArangoDBVersion.class);
				doReturn("ArangoDB").when(version).getServer();
				doReturn("3.4.0").when(version).getVersion();
				doReturn(License.COMMUNITY).when(version).getLicense();
				doReturn(version).when(arango).getVersion();
			}
			doReturn(arango).when(operations).driver();
		}
		final ArangoHealthIndicator indicator = new ArangoHealthIndicator(operations);
		assertThat(indicator.health().getStatus()).isEqualTo(Status.UP);
	}

}
