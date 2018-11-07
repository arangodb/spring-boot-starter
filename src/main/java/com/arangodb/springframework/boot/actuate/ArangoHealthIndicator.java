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

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.util.Assert;

import com.arangodb.entity.ArangoDBVersion;
import com.arangodb.springframework.core.ArangoOperations;

/**
 * Simple {@link HealthIndicator} returning status information of ArangoDB
 *
 * @author Mark Vollmary
 *
 */
public class ArangoHealthIndicator extends AbstractHealthIndicator {

	private final ArangoOperations operations;

	public ArangoHealthIndicator(final ArangoOperations operations) {
		super("ArangoDB health check failed");
		Assert.notNull(operations, "ArangoOperations must not be null");
		this.operations = operations;
	}

	@Override
	protected void doHealthCheck(final Health.Builder builder) throws Exception {
		final ArangoDBVersion version = operations.driver().getVersion();
		builder.up()
			.withDetail("server", version.getServer())
			.withDetail("version", version.getVersion())
			.withDetail("license", version.getLicense());
	}

}
