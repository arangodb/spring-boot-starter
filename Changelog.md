# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/).

## [Unreleased]

- Spring Boot 3.3 support (DE-816)
- updated `arangodb-spring-data` to version `4.2.0`

## [3.2-0] - 2024.01.24

- Spring Boot 3.2 support (DE-767)
- updated `arangodb-spring-data` to version `4.1.0`

## [3.1-2] - 2024.01.16

- updated `arangodb-spring-data` to version `4.0.1`
- fixed returning `com.arangodb.internal.InternalArangoDBBuilder` from public API (#25)

## [3.1-1] - 2023.09.21

- re-enabled bean method proxies (#23)

## [3.1-0] - 2023.09.19

- Spring Boot 3.1 support

## [3.0-0] - 2023.09.19

- Spring Boot 3.0 support (#21)
- updated Spring Data ArangoDB to version 4.0 and ArangoDB Java Driver to version 7.1 (#21)
- raised required minimum Java version to JDK 17 (#21)
- added `jwt` and `acquireHostListInterval` configuration properties (#21)

## [2.7-0] - 2023.02.08

- updated `arangodb-spring-data` to `3.8.0`

## [2.6-0] - 2022.02.09

- changed prefix of configuration properties to `arangodb.spring.data.*`
- updated `arangodb-spring-data` to `3.7.0`
- migrated Health Indicator to Spring Boot 2.4

## [2.5-0] - 2022.02.09

- changed prefix of configuration properties to `arangodb.spring.data.*`
- updated `arangodb-spring-data` to `3.7.0`
- migrated Health Indicator to Spring Boot 2.4

## [2.4-0] - 2022.02.09

- changed prefix of configuration properties to `arangodb.spring.data.*`
- updated `arangodb-spring-data` to `3.7.0`
- migrated Health Indicator to Spring Boot 2.4

## [2.2.7.RELEASE] - 2020.05.14

- adopted new versioning scheme matching the Spring Boot versions

## [1.0.3] - 2020.05.07

- dependencies update

## [1.0.2] - 2019.09.04

- updated arangodb-spring-data to 3.2.2

## [1.0.1] - 2018-11-12

### Added

- added `spring-boot-configuration-processor`
- added JavaDoc in `ArangoProperties`

### Changed

- made dependency `spring-boot-actuator` optional

## [1.0.0] - 2018-11-09

- initial release