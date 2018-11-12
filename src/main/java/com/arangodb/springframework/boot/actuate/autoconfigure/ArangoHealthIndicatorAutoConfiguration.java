/**
 *
 */
package com.arangodb.springframework.boot.actuate.autoconfigure;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthIndicatorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.health.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.arangodb.springframework.boot.actuate.ArangoHealthIndicator;
import com.arangodb.springframework.boot.autoconfigure.ArangoAutoConfiguration;
import com.arangodb.springframework.core.ArangoOperations;

/**
 * @author Mark Vollmary
 *
 */
@Configuration
@ConditionalOnClass({ ArangoOperations.class, HealthIndicator.class, HealthIndicatorAutoConfiguration.class })
@ConditionalOnBean(ArangoOperations.class)
@ConditionalOnEnabledHealthIndicator("arango")
@AutoConfigureBefore(HealthIndicatorAutoConfiguration.class)
@AutoConfigureAfter(ArangoAutoConfiguration.class)
public class ArangoHealthIndicatorAutoConfiguration
		extends CompositeHealthIndicatorConfiguration<ArangoHealthIndicator, ArangoOperations> {

	private final Map<String, ArangoOperations> operations;

	public ArangoHealthIndicatorAutoConfiguration(final Map<String, ArangoOperations> operations) {
		super();
		this.operations = operations;
	}

	@Bean
	@ConditionalOnMissingBean(name = "arangoHealthIndicator")
	public HealthIndicator arangoHealthIndicator() {
		return createHealthIndicator(operations);
	}

}
