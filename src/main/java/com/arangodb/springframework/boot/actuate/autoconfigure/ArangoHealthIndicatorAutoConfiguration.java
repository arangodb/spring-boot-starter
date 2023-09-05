package com.arangodb.springframework.boot.actuate.autoconfigure;

import com.arangodb.springframework.boot.actuate.ArangoHealthIndicator;
import com.arangodb.springframework.boot.autoconfigure.ArangoAutoConfiguration;
import com.arangodb.springframework.core.ArangoOperations;
import org.springframework.boot.actuate.autoconfigure.health.CompositeHealthContributorConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @author Mark Vollmary
 * @author Michele Rastelli
 */
@AutoConfiguration
@ConditionalOnClass({ ArangoOperations.class, HealthIndicator.class, HealthContributorAutoConfiguration.class })
@ConditionalOnBean(ArangoOperations.class)
@ConditionalOnEnabledHealthIndicator("arango")
@AutoConfigureAfter(ArangoAutoConfiguration.class)
public class ArangoHealthIndicatorAutoConfiguration
		extends CompositeHealthContributorConfiguration<ArangoHealthIndicator, ArangoOperations> {

	@Bean
	@ConditionalOnMissingBean(name = "arangoHealthIndicator")
	public HealthContributor arangoHealthIndicator(Map<String, ArangoOperations> operations) {
		return createContributor(operations);
	}

}
