# Spring Boot Starter ArangoDB - Getting Started

## Supported versions

| Spring Boot Starter ArangoDB | Spring Data ArangoDB | Spring Data |
| ---------------------------- | -------------------- | ----------- |
| 1.0.0                        | 3.2.x                | 2.1.x       |

## Maven

Add `arangodb-spring-boot-starter` to your project to auto configure Spring Data ArangoDB.

```xml
<dependency>
  <groupId>com.arangodb</groupId>
  <artifactId>arangodb-spring-boot-starter</artifactId>
  <version>1.0.0</version>
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
