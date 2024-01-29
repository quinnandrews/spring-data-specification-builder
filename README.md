# Spring Data Specification Builder

## Description
Provides a variety of components that reduce the overhead of composing and maintaining Specifications.

[SpecificationFactory ](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationFactory.java)is used to generate Specification instances. It encapsulates anonymous Specification subclasses and provides null-safe handling. 

[SpecificationBuilder](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationBuilder.java) puts a fluent API on top of SpecificationFactory to compose compound Specifications with ease. 

[SpecificationUtil](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationUtil.java) is used by SpecificationFactory to assist with null checking, wildcard detection and String conversions, etc. 

The [Specifications Annotation](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/annotations/Specifications.java) is available as a convenience, an alias of Spring's Component Annotation to mark Specification Beans as a particular kind of Bean.

SpecificationFactory and SpecificationUtil may be used independently, if desired. However, the intent is to use SpecificationBuilder exclusively, without being aware of either SpecificationFactory or SpecificationUtil, but it is not mandatory. Both SpecificationFactory and SpecificationUtil are declared with public access.

## Features

- Built-in null handling makes conditional query composition simple and easy – no need to wrap Specification conjunctions with a check for parameter state when parameters are optional.
- Built-in support for efficient eager fetching provides query optimization – an entire Aggregate can be loaded with one query instead of many.
- A fluent API encapsulating boilerplate code makes queries that are both strongly typed and easy to read – the risk of error is reduced while query logic is more coherent.
- A `@Specifications` Annotation complements Spring's `@Controller`, `@Service` and `@Repository` Annotations – Specification Beans can be identified as a special kind of Bean by both developers and processes (like the execution of rules with [ArchUnit](https://github.com/TNG/ArchUnit), for example).

## Requirements
### Java 17
https://adoptium.net/temurin/releases/?version=17

### Hibernate JPA Metamodel Generator (or equivalent)
https://hibernate.org/orm/tooling/

Add the Hibernate JPA Metamodel Generator to the Maven Compiler Plugin as an Annotation Processor:
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <annotationProcessorPaths>
            <path>
                <groupId>org.hibernate.orm</groupId>
                <artifactId>hibernate-jpamodelgen</artifactId>
                <version>6.3.1.Final</version>
            </path>
        </annotationProcessorPaths>
    </configuration>
</plugin>
```

## Dependencies
- Spring Boot Starter Data JPA 3.1.4
- Apache Commons Lang 3.13.0

## Usage
Add this project's artifact to your project as a dependency:
```xml
<dependency>
    <groupId>io.github.quinnandrews</groupId>
    <artifactId>spring-data-specification-builder</artifactId>
    <version>2.0.0</version>
</dependency>
```
(NOTE: This project's artifact is NOT yet available in Maven Central, but is available from GitHub Packages.)

Then extend your Repository Interfaces with JPASpecificationExecutor so that Specification methods are available:
```java
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GuitarPedalRepository extends JpaRepository<GuitarPedal, Long>,
                                               JpaSpecificationExecutor<GuitarPedal> {
}
```
Next, define your Specifications in a Specifications Bean (this is optional – you can also define Specification queries where they are used, in a Service, a Test, etc.)

```java
import io.github.quinnandrews.spring.data.specification.annotations.Specifications;
import io.github.quinnandrews.spring.data.specification.builder.SpecificationBuilder;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import org.springframework.data.jpa.domain.Specification;

@Specifications
public class GuitarPedalSpecifications {

    public Specification<GuitarPedal> pedalsNotSoldWithValueGreaterThan(final Integer usedValue) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .where().isNull(GuitarPedal_.dateSold)
                .and().isGreaterThan(GuitarPedal_.usedValue, usedValue)
                .toSpecification();
    }
}
```
Finally, use your Specifications to execute queries:
```java
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.repository.GuitarPedalRepository;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.specifications.GuitarPedalSpecifications;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GuitarPedalService {
    
    private final GuitarPedalSpecifications guitarPedalSpecifications;
    private final GuitarPedalRepository guitarPedalRepository;

    public GuitarPedalService(final GuitarPedalSpecifications guitarPedalSpecifications, 
                              final GuitarPedalRepository guitarPedalRepository) {
        this.guitarPedalSpecifications = guitarPedalSpecifications;
        this.guitarPedalRepository = guitarPedalRepository;
    }
    
    public List<GuitarPedal> getPedalsNotSoldWithValueGreaterThan(final Integer usedValue) {
        return guitarPedalRepository.findAll(
                guitarPedalSpecifications.pedalsNotSoldWithValueGreaterThan(usedValue), 
                Sort.by(GuitarPedal_.NAME)
        );
    }
}
```

## Examples
The examples and tests use the Domain of Guitar Pedals (I'm a musician). It was simply more fun than using the Domains of TODOs or Employees.

### GuitarPedalSpecifications.class
[GuitarPedalSpecifications](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/application/data/guitarpedals/specifications/GuitarPedalSpecifications.java) contains the most comprehensive set of examples. It compares defining the same queries with and without the SpecificationBuilder, details gotchas and goes into more complex things like working with collections, for instance. Begin from the top and work your way down. 

### GuitarPedalSpecificationsIntegrationTest.class
[GuitarPedalSpecificationsIntegrationTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/GuitarPedalSpecificationsIntegrationTest.java) contains the Integrations Tests of the examples in GuitarPedalSpecifications. This may be useful to look at as well, or to run the examples yourself and see the sql output with your own eyes.

### SpecificationBuilderIntegrationTest.class
[SpecificationBuilderIntegrationTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationBuilderIntegrationTest.java) contains Integration Tests for the methods in SpecificationBuilder. Some of these tests cover cases that are not included in GuitarPedalSpecifications.

### Other Test Classes
[SpecificationBuilderTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationBuilderTest.java), [SpecificationFactoryTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationFactoryTest.java) and [SpecificationUtilTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationUtilTest.java) contain Unit Tests for the methods in their corresponding Classes. These may be useful to look at as well, in order to understand more about how things work under the hood, but it is not necessary. 

## Roadmap
1) **Build Specifications on Associations**<br>
Add versions of `where` methods that operate on Associations. It is expected the builder will need to maintain an instance variable containing Joins already created, so that they can be re-used during the build process if there is more than one Specification to apply to an Association.
2) **Define JoinType of Associations**<br>
Add versions of `fetchOf()` that allow definition of JoinType. (Should it be applied to `where` methods on Associations as well?)
3) **Add a `not()` Method in the Builder**
4) **Add a `clear()` Method in the Builder**
5) **Implement a SortBuilder to complement the SpecificationBuilder**<br/>
Implement with the same sort of fluent-api and require Attributes instead of Strings for type safety.
