# Spring Boot Starter ArangoDB - Getting Started

## Supported versions

Spring Boot Starter ArangoDB is compatible with all supported stable versions of ArangoDB server, see 
[Product Support End-of-life Announcements](https://www.arangodb.com/eol-notice){:target="_blank"}.


It is released in the following versions, each one compatible with the corresponding versions of Spring Boot, 
Spring Framework, Spring Data ArangoDB, ArangoDB Java Driver:

| Spring Boot Starter ArangoDB | Spring Boot | Spring Framework | Spring Data ArangoDB | ArangoDB Java Driver |
|------------------------------|-------------|------------------|----------------------|----------------------|
| 3.1-x                        | 3.1         | 6.0              | 4.0                  | 7.1                  |
| 3.0-x                        | 3.0         | 6.0              | 4.0                  | 7.1                  |
| 2.7-x                        | 2.7         | 5.3              | 3.10                 | 6.25                 |


Note that the adopted versioning scheme does not honour the semantic versioning rules, indeed minor or patch
releases could introduce new features or breaking changes. Please refer to 
[releases](https://github.com/arangodb/spring-boot-starter/releases) for details.  


## Maven

Add `arangodb-spring-boot-starter` to your project to auto configure Spring Data ArangoDB.

```xml
<dependency>
  <groupId>com.arangodb</groupId>
  <artifactId>arangodb-spring-boot-starter</artifactId>
  <version>3.x-y</version>
</dependency>
```

## Configuration

Configure the properties files of your application with the properties of [ArangoProperties](https://github.com/mpv1989/spring-boot-starter/blob/master/src/main/java/com/arangodb/springframework/boot/autoconfigure/ArangoProperties.java).

```
arangodb.spring.data.database=mydb
arangodb.spring.data.user=root
arangodb.spring.data.password=1234
```

## Monitor

ArangoDB health monitoring can be applied to your application by adding `spring-boot-starter-actuator` to your project and call HTTP `GET /actuator/health` against your application.

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
