# Spring Data Specification Builder

## Description
Reduces the overhead of composing and maintaining Specifications. Enhances code legibility with a fluent API, makes conditional query building easy and supports query optimization for fetching associated Entities eagerly.

## Features
- Built-in null-safe handling simplifies conditional query composition – no need to wrap Specification conjunctions with a check for parameter state when parameters are optional.
- A fluent API encapsulating boilerplate code composes queries that are both strongly typed and easy to read – the risk of error is reduced while query logic is more coherent.
- Built-in support for efficient eager fetching provides query optimization – an entire Aggregate can be loaded with one query instead of many.
- A `@Specifications` Annotation complements Spring's `@Controller`, `@Service` and `@Repository` Annotations – Specification Beans can be identified as a special kind of Bean by both developers and processes (like the execution of rules with [ArchUnit](https://github.com/TNG/ArchUnit), for example).

## Components
[SpecificationFactory ](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationFactory.java) generates Specification instances, encapsulating anonymous Specification subclasses with null-safe handling. 

[SpecificationBuilder](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationBuilder.java) puts a fluent API on top of SpecificationFactory to compose compound Specifications with a fluent style. 

[SpecificationUtil](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationUtil.java) assists with null checking, wildcard detection and String conversions among other things. 

[Specifications Annotation](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/main/java/io/github/quinnandrews/spring/data/specification/annotations/Specifications.java) is available as a convenience, an alias of Spring's Component Annotation to use when Specifications are contained within a Spring Bean.

NOTE: While SpecificationFactory and SpecificationUtil may be used independently, the intent is to use SpecificationBuilder exclusively while client code remains agnostic of either SpecificationFactory or SpecificationUtil. Nonetheless, SpecificationFactory and SpecificationUtil are declared with public access to support a diversity of approaches.

## Requirements
### Java 17
https://adoptium.net/temurin/releases/?version=17

### Hibernate JPA Metamodel Generator (or equivalent)
https://hibernate.org/orm/tooling/

## Transitive Dependencies
- Spring Boot Starter Data JPA 3.1.4
- Apache Commons Lang 3.13.0

## Usage
### Add Spring Data Specification Builder
Add the `spring-data-specification-builder` artifact to your project as a dependency:
```xml
<dependency>
    <groupId>io.github.quinnandrews</groupId>
    <artifactId>spring-data-specification-builder</artifactId>
    <version>2.0.0</version>
</dependency>
```
(NOTE: The `spring-data-specification-builder` artifact is NOT yet available in Maven Central, but is available from GitHub Packages, which requires additional configuration in your pom.xml file.)

### Add Hibernate JPA Metamodel Generator
Add the Hibernate JPA Metamodel Generator to the Maven Compiler Plugin as an Annotation Processor, which will generate Attribute Classes representing your Entity properties for use with Specification Queries to provide type safety:
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

### Extend JPASpecificationExecutor
Extend your Repository Interfaces with JPASpecificationExecutor in order to access Spring Data's Specification methods:
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

### Build a Specification Query
#### Alternative 1: Using SpecificationBuilder Inside a Service Bean
This is how this project is meant to be used. You simply build your Specifications inline, within the Service (or whatever Class needs to use them). 

When adding a query method to a Repository, typically it will only be called from one method in one Service. So why define it in the Repository and take on the added overhead? It's simply much simpler and effective to define the query logic in the Service for those cases.

In other cases, however, one may not want to add a query method to a Repository because it's used in a testing context. For example, an Integration Test may need to verify the state of data in the database with a custom query. Adding methods to a Repository in the main source to support code in the test source is not good practice.

Note that the argument `usedValue` is not required. If it's null, the Builder and Factory will handle it gracefully, and the rendered SQL will NOT contain a greater than clause. There is no need to wrap `isGreaterThan(GuitarPedal_.usedValue, usedValue)` with a conditional.

```java
import io.github.quinnandrews.spring.data.specification.builder.SpecificationBuilder;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.repository.GuitarPedalRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GuitarPedalService {
    
    private final GuitarPedalRepository guitarPedalRepository;

    public GuitarPedalService(final GuitarPedalRepository guitarPedalRepository) {
        this.guitarPedalRepository = guitarPedalRepository;
    }
    
    public List<GuitarPedal> getPedalsNotSoldWithValueGreaterThan(final Integer usedValue) {
        return guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNull(GuitarPedal_.dateSold)
                        .and().isGreaterThan(GuitarPedal_.usedValue, usedValue)
                        .toSpecification(), 
                Sort.by(GuitarPedal_.NAME)
        );
    }
}
```

#### Alternative 2: Using SpecificationBuilder inside a Specifications Bean
Some may prefer to define their queries in isolation from Services for decoupling and reuse. In that case, one can build their Specifications in a Class annotated with `@Specifications` – declaring it as a Spring Bean – then inject it into the Service as one does with a Repository.

This approach can be useful for testing, depending on your strategy, because it's easy to mock or spy the Specifications Bean.

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

#### Alternative 3: Using SpecificationFactory inside a Specifications Class with Static Methods
In this example, static methods are used to build Specifications with the SpecificationFactory directly, and each clause has been broken down into its own method (though they could have been defined in one method as a compound Specification as was done before). The Factory still encapsulates the boilerplate while providing null safety, but there is no need for Dependency Injection. Static imports can be used with Spring's Specification Interface instead, reducing the amount of code needed in the Service.

Breaking down each clause into its own method and using Spring's Specification Interface also makes the code highly readable and meaningful. It actually becomes more legible and fluent than using the SpecificationBuilder's fluent API, and what it means for a GuitarPedal to have been sold is decoupled from any code depending on it, which is more aligned with how Specifications are meant to be used.

Alternatively, instead of having a distinct Class to contain the Specifications, one could define the Class as an inner Class of the Entity. This may, perhaps, be the best approach from a design point of view, since the Entity would own and control the Specifications declared for it. 

Finally, instead of using static methods, one could, of course, define the same methods on a Specifications Bean and inject it into the Service, as was done in Alternative 2, which may preferred for testing purposes among other reasons. 
```java
import io.github.quinnandrews.spring.data.specification.builder.SpecificationFactory;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import org.springframework.data.jpa.domain.Specification;

public class GuitarPedalSpecifications {
    
    public static Specification<GuitarPedal> hasNotBeenSold() {
        return SpecificationFactory.isNull(GuitarPedal_.dateSold);
    }

    public static Specification<GuitarPedal> usedValueIsGreaterThan(final Integer usedValue) {
        return SpecificationFactory.isGreaterThan(GuitarPedal_.usedValue, usedValue);
    }
}
```
```java
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.repository.GuitarPedalRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.specifications.GuitarPedalSpecifications.*;

@Service
public class GuitarPedalService {

    private final GuitarPedalRepository guitarPedalRepository;

    public GuitarPedalService(final GuitarPedalRepository guitarPedalRepository) {
        this.guitarPedalRepository = guitarPedalRepository;
    }

    public List<GuitarPedal> getPedalsNotSoldWithValueGreaterThan(final Integer usedValue) {
        return guitarPedalRepository.findAll(
                Specification.where(hasNotBeenSold())
                        .and(usedValueIsGreaterThan(usedValue)),
                Sort.by(GuitarPedal_.NAME)
        );
    }
}
```

#### Alternative 4: Using SpecificationFactory inside a Service Bean
If you're looking for the most minimal approach, this example is for you. In this case the SpecificationFactory is used directly in the Service with Spring's Specification Interface. As before, boilerplate remains encapsulated and null-safe handling is provided. 

```java
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.repository.GuitarPedalRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import static io.github.quinnandrews.spring.data.specification.builder.SpecificationFactory.*;

@Service
public class GuitarPedalService {

    private final GuitarPedalRepository guitarPedalRepository;

    public GuitarPedalService(final GuitarPedalRepository guitarPedalRepository) {
        this.guitarPedalRepository = guitarPedalRepository;
    }

    public List<GuitarPedal> getPedalsNotSoldWithValueGreaterThan(final Integer usedValue) {
        return guitarPedalRepository.findAll(
                Specification.where(isNull(GuitarPedal_.dateSold))
                        .and(isGreaterThan(GuitarPedal_.usedValue, usedValue)),
                Sort.by(GuitarPedal_.NAME)
        );
    }
}
```

### Efficient Eager Fetching of Associations
Associations can be fetched eagerly with their Entities using only one query instead of many – a great optimization technique. While there are a number of ways to do this, the SpecificationBuilder makes it a simple process. 

In this example, it's the same query used previously, but two clauses have been added. The first fetches each GuitarPedal's Manufacturer (a many-to-one relationship) while the second fetches each GuitarPedal's Tags (a one-to-many relationship).

The rendered SQL will get all the necessary data with just one query, rather than executing one query to get the GuitarPedal data followed by two additional queries for each Entity instance, one to get the associated Manufacturer data, the other to get the associated Tag data.

```java
@Service
public class GuitarPedalService {

    private final GuitarPedalRepository guitarPedalRepository;

    public GuitarPedalService(final GuitarPedalRepository guitarPedalRepository) {
        this.guitarPedalRepository = guitarPedalRepository;
    }

    public List<GuitarPedal> getPedalsNotSoldWithValueGreaterThan(final Integer usedValue) {
        return guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .with().fetchOf(GuitarPedal_.manufacturer)
                        .and().fetchOf(GuitarPedal_.tags)
                        .where().isNull(GuitarPedal_.dateSold)
                        .and().isGreaterThan(GuitarPedal_.usedValue, usedValue)
                        .toSpecification(),
                Sort.by(GuitarPedal_.NAME)
        );
    }
}
```

## Examples
### GuitarPedalSpecifications.class
[GuitarPedalSpecifications](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/application/data/guitarpedals/specifications/GuitarPedalSpecifications.java) contains the most comprehensive set of examples. It compares defining the same queries with and without the SpecificationBuilder, details gotchas and goes into more complex things, like working with collections and filtering by properties belonging to those associations. Begin from the top and work your way down. 

### GuitarPedalSpecificationsIntegrationTest.class
[GuitarPedalSpecificationsIntegrationTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/GuitarPedalSpecificationsIntegrationTest.java) contains Integration Tests for the examples in GuitarPedalSpecifications. This may be useful to look at as well, or to run the examples yourself and see the sql output.

### SpecificationBuilderIntegrationTest.class
[SpecificationBuilderIntegrationTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationBuilderIntegrationTest.java) contains Integration Tests for the methods in SpecificationBuilder. Some of these tests cover cases not included in GuitarPedalSpecifications.

### Other Test Classes
[SpecificationBuilderTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationBuilderTest.java), [SpecificationFactoryTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationFactoryTest.java) and [SpecificationUtilTest](https://github.com/quinnandrews/spring-data-specification-builder/blob/a93b9a84805d3c20b1461ca634abd3a50695d245/src/test/java/io/github/quinnandrews/spring/data/specification/builder/SpecificationUtilTest.java) contain Unit Tests for the methods in their corresponding Classes. These may be useful to look at as well, in order to understand the implementation details, if one is interested. 

## Roadmap
1) **Build Specifications on Associations**<br>
Add versions of `where` methods that operate on Associations. It is expected the builder will need to maintain an instance variable containing Joins already created, so that they can be re-used during the build process if there is more than one Specification to apply to an Association.
2) **Define JoinType of Associations**<br>
Add versions of `fetchOf()` that allow definition of JoinType. (Should it be applied to `where` methods on Associations as well?)
3) **Add a `not()` Method in the Builder**
4) **Add a `clear()` Method in the Builder**
5) **Implement a SortBuilder to complement the SpecificationBuilder**<br/>
Use the same sort of fluent-api and require Attributes instead of Strings for type safety.
