package com.arangodb.springframework.boot.actuate.autoconfigure;

import java.util.Map;

import org.springframework.boot.actuate.autoconfigure.health.*;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
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
 * @author Michele Rastelli
 */
@Configuration(proxyBeanMethods = false)
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
