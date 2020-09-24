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

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.arangodb.Protocol;
import com.arangodb.entity.LoadBalancingStrategy;
import com.arangodb.internal.ArangoDefaults;

/**
 * @author Mark Vollmary
 *
 */
@ConfigurationProperties(prefix = "arangodb.spring.data")
public class ArangoProperties {

	/**
	 * Database name.
	 */
	private String database = "_system";

	/**
	 * Hosts to connect to. Multiple hosts can be added to provide fallbacks in a
	 * single server with active failover or load balancing in an cluster setup.
	 */
	private Collection<String> hosts = new ArrayList<>();

	/**
	 * Username to use for authentication.
	 */
	private String user = ArangoDefaults.DEFAULT_USER;

	/**
	 * Password for the user for authentication.
	 */
	private String password;

	/**
	 * Connection and request timeout in milliseconds.
	 */
	private Integer timeout = ArangoDefaults.DEFAULT_TIMEOUT;

	/**
	 * If set to {@code true} SSL will be used when connecting to an ArangoDB
	 * server.
	 */
	private Boolean useSsl = ArangoDefaults.DEFAULT_USE_SSL;

	/**
	 * Maximum number of connections the built in connection pool will open per
	 * host.
	 */
	private Integer maxConnections = ArangoDefaults.MAX_CONNECTIONS_VST_DEFAULT;

	/**
	 * Maximum time to life of a connection.
	 */
	private Long connectionTtl;

	/**
	 * Whether or not the driver should acquire a list of available coordinators in
	 * an ArangoDB cluster or a single server with active failover.
	 */
	private Boolean acquireHostList = ArangoDefaults.DEFAULT_ACQUIRE_HOST_LIST;

	/**
	 * Load balancing strategy to be used in an ArangoDB cluster setup.
	 */
	private LoadBalancingStrategy loadBalancingStrategy = ArangoDefaults.DEFAULT_LOAD_BALANCING_STRATEGY;

	/**
	 * Network protocol to be used to connect to ArangoDB.
	 */
	private Protocol protocol = ArangoDefaults.DEFAULT_NETWORK_PROTOCOL;

	public ArangoProperties() {
		super();
	}

	public final String getDatabase() {
		return database;
	}

	public final void setDatabase(final String database) {
		this.database = database;
	}

	public final String getUser() {
		return user;
	}

	public final void setUser(final String user) {
		this.user = user;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(final String password) {
		this.password = password;
	}

	public final Collection<String> getHosts() {
		return hosts;
	}

	public final void setHosts(final Collection<String> hosts) {
		this.hosts = hosts;
	}

	public final Integer getTimeout() {
		return timeout;
	}

	public final void setTimeout(final Integer timeout) {
		this.timeout = timeout;
	}

	public final Boolean getUseSsl() {
		return useSsl;
	}

	public final void setUseSsl(final Boolean useSsl) {
		this.useSsl = useSsl;
	}

	public final Integer getMaxConnections() {
		return maxConnections;
	}

	public final void setMaxConnections(final Integer maxConnections) {
		this.maxConnections = maxConnections;
	}

	public final Long getConnectionTtl() {
		return connectionTtl;
	}

	public final void setConnectionTtl(final Long connectionTtl) {
		this.connectionTtl = connectionTtl;
	}

	public final Boolean getAcquireHostList() {
		return acquireHostList;
	}

	public final void setAcquireHostList(final Boolean acquireHostList) {
		this.acquireHostList = acquireHostList;
	}

	public final LoadBalancingStrategy getLoadBalancingStrategy() {
		return loadBalancingStrategy;
	}

	public final void setLoadBalancingStrategy(final LoadBalancingStrategy loadBalancingStrategy) {
		this.loadBalancingStrategy = loadBalancingStrategy;
	}

	public final Protocol getProtocol() {
		return protocol;
	}

	public final void setProtocol(final Protocol protocol) {
		this.protocol = protocol;
	}

}
