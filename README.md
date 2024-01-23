# Spring Data Specification Builder

## Description
Provides a variety of components that reduce the overhead of composing and maintaining Specifications.

SpecificationFactory is used to generate Specification instances. It encapsulates anonymous Specification subclasses and provides null-safe handling. 

SpecificationBuilder puts a fluent API on top of SpecificationFactory to compose compound Specifications with ease. 

SpecificationUtil is used by SpecificationFactory to assist with null checking, wildcard detection and String conversions, etc. 

The Specifications Annotation is available as a convenience, an alias of Spring's Component Annotation to mark Specification Beans as a distinct type.

SpecificationFactory and SpecificationUtil may be used independently, if desired. However, the intent is to use SpecificationBuilder exclusively, without being aware of either SpecificationFactory or SpecificationUtil, but it is not mandatory. Both SpecificationFactory and SpecificationUtil are declared with public access.

## Features

- Built-in null handling makes conditional query composition simple and easy – no need to wrap Specification conjunctions with a check for parameter state when parameters are optional.
- Built-in support for efficient eager fetching provides query optimization – an entire Aggregate can be loaded with one query instead of many.
- A fluent API encapsulating boilerplate code makes queries that are both strongly typed and easy to read – the risk of error is reduced while comprehension of query logic is enhanced.
- A `@Specifications` Annotation complements Spring's `@Controller`, `@Service` and `@Repository` Annotations, allowing one to declare Specification Beans in a similar fashion and being able to identify these kinds of Beans for special use cases, like when defining rules with ArchUnit, for example.

## Requirements
### Java 17
https://adoptium.net/temurin/releases/?version=17

### Hibernate JPA Metamodel Generator (or equivalent)
https://hibernate.org/orm/tooling/
```xml
<dependency>
    <groupId>org.hibernate.orm</groupId>
    <artifactId>hibernate-jpamodelgen</artifactId>
    <version>6.3.1.Final</version>
</dependency>
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
    <version>1.0.0</version>
</dependency>
```
(NOTE: This project's artifact is NOT yet available in Maven Central, but is available from GitHub Packages.)

Then extend your Repository Interfaces with JPASpecificationExecutor:
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
Next, define your Specifications:

```java
import io.github.quinnandrews.spring.data.specification.annotations.Specifications;
import io.github.quinnandrews.spring.data.specification.builder.SpecificationBuilder;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import org.springframework.data.jpa.domain.Specification;

@Specifications
public class GuitarPedalSpecifications {

    public Specification<GuitarPedal> pedalsNotSoldWithValueGreaterThan(final Integer usedValue) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
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
The examples and tests use the Domain of Guitar Pedals (I'm also a musician and I enjoy exploring the vast array of sounds offered by the world of guitar pedals). It was simply more fun than using the Domains of TODOs or Employees, for example.

### GuitarPedalSpecifications.class
GuitarPedalSpecifications contains the most comprehensive set of examples. It compares defining the same queries with and without the SpecificationBuilder, details gotchas and goes into more complex things like working with collections, for instance. Begin from the top and work your way down. 

### GuitarPedalSpecificationsIntegrationTest.class
GuitarPedalSpecificationsIntegrationTest contains the Integrations Tests of the examples in GuitarPedalSpecifications. This may be useful to look at as well, or to run the examples yourself and see the sql output with your own eyes.

### SpecificationBuilderIntegrationTest.class
SpecificationBuilderIntegrationTest contains Integration Tests for the methods in SpecificationBuilder. Some of these tests cover cases that are not included in GuitarPedalSpecifications.

### Other Test Classes
SpecificationBuilderTest, SpecificationFactoryTest and SpecificationUtilTest contain Unit Tests for the methods in their corresponding Classes. These may useful to look at as well, in order to understand more about how things work under the hood, but it is not necessary. 

## Roadmap
1) ~~**Add `and()` Methods in the Builder**<br>
Add `and()` Methods to make the fluent-API more fluent & legible, and to better resemble the Specification Interface.~~
2) **Build Specifications on Associations**<br>
Add versions of `where` methods that operate on Associations. It is expected the builder will need to maintain an instance variable containing Joins already created, so that they can be re-used during the build process if there is more than one Specification to apply to an Association.
3) **Define JoinType of Associations**<br>
Add versions of `withFetch()` that allow definition of JoinType. Should it be applied to `where` methods on Associations as well?
4) **Consider Adding a `not()` Method in the Builder**
5) **Consider Adding a `clear()` Method in the Builder**
