package io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.specifications;

import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedalTag;
import jakarta.persistence.criteria.Join;
import io.github.quinnandrews.spring.data.specification.annotations.Specifications;
import io.github.quinnandrews.spring.data.specification.builder.SpecificationBuilder;
import io.github.quinnandrews.spring.data.specification.builder.SpecificationFactory;
import io.github.quinnandrews.spring.data.specification.builder.SpecificationUtil;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

/**
 * <p> Examples using the Builder and Factory classes, including comparisons
 * with examples that use Spring's Specification Class directly. The
 * corresponding integration test (GuitarPedalSpecificationsIntegrationTest)
 * executes these examples with various conditions.
 *
 * <p> When using Spring's Specification feature, it's a common practice to
 * define Specifications in a Class of their own, so that they can be reused
 * and/or chained together as necessary. However, it is not required.
 *
 * @author Quinn Andrews
 */
@Specifications
public class GuitarPedalSpecifications {

    private final SpecificationFactory specificationFactory = new SpecificationFactory();

    public GuitarPedalSpecifications() {
        // no-op
    }

    /*
    This example, Search Example #1, uses Spring's API directly to define and chain 2
    Specifications into one.

    It isn't easy to read, given the lambda expressions and the references to the JPA
    Criteria API, and the individual Specifications can't be reused independently.

    The next example, Search Example #2, fixes that.
     */
    public Specification<GuitarPedal> search_example_01(final Integer usedValueGreaterThan) {
        return Specification.where((Specification<GuitarPedal>) (root, query, builder) ->
                        builder.greaterThan(root.get(GuitarPedal_.usedValue), usedValueGreaterThan))
                .and((root, query, builder) ->
                        builder.isNull(root.get(GuitarPedal_.dateSold)));
    }

    /*
    This example, Search Example #2, also uses Spring's API directly, doing the same thing
    that Example #1 does, but the two Specifications have now been moved to methods of their
    own.

    This example reads really well and the two Specifications can be reused independently
    of each other and this method that chains them together.

    However, there are still some issues:

    1) The code in hasNotBeenSold() and usedValueGreaterThan() isn't as easy to read as
    it could be.

    2) The code in hasNotBeenSold() and usedValueGreaterThan() is predisposed to becoming
    boilerplate code.

    3) More importantly, however, the code in usedValueGreaterThan() is not null-safe. What
    would happen if the argument 'usedValueGreaterThan' is null? No Exceptions would be
    thrown – it isn't that kind of null-safety issue. But the SQL query wouldn't be rendered
    as expected.

    The SQL query would be rendered like this:

    select
        g1_0.id,
        g1_0.date_purchased,
        g1_0.date_sold,
        g1_0.has_stereo_output,
        g1_0.manufacturer_id,
        g1_0.name,
        g1_0.used_value
    from
        guitar_pedal g1_0
    where
        g1_0.date_sold is null
        and g1_0.used_value>?

    Even though the argument 'usedValueGreaterThan' was null, the Predicate was still rendered
    to the query's where clause, and the query returns an empty result set when executed.

    One of the reasons the underlying Criteria API exists, other than providing type safety,
    is to support conditional query building. After all, there are many cases where search
    or filter parameters are optional, and in those cases the query building process must
    provide default handling, either by defining default values, or by doing nothing when
    the parameter has been undefined, to prevent rendering a Predicate to the query's where
    clause. But this means the query building process needs to perform null checks in one
    form or another.

    In the next example, Search Example #3, we add a null check.
     */
    public Specification<GuitarPedal> search_example_02(final Integer usedValueGreaterThan) {
        return Specification.where(hasNotBeenSold())
                .and(usedValueGreaterThan(usedValueGreaterThan));
    }

    public Specification<GuitarPedal> hasNotBeenSold() {
        return (root, query, builder) -> builder.isNull(root.get(GuitarPedal_.dateSold));
    }

    public Specification<GuitarPedal> usedValueGreaterThan(final Integer value) {
        return (root, query, builder) -> builder.greaterThan(root.get(GuitarPedal_.usedValue), value);
    }

    /*
    This example, Search Example #3, also uses Spring's API directly, doing the same thing
    that Example #1 and #2 do, but unlike those examples, #3 adds a check that will only add
    the second Specification if the argument 'usedValueGreaterThan' is null.

    If the argument 'usedValueGreaterThan' is null, then the SQL query would be rendered as
    expected, like this:

    select
        g1_0.id,
        g1_0.date_purchased,
        g1_0.date_sold,
        g1_0.has_stereo_output,
        g1_0.manufacturer_id,
        g1_0.name,
        g1_0.used_value
    from
        guitar_pedal g1_0
    where
        g1_0.date_sold is null

    Unlike Example #2, the where clause does not include the predicate 'and g1_0.used_value>?',
    and the query returns a total of 3 Guitar Pedals with the given test data when executed.

    But there are a number of problems that remain:

    1) The legibility in this example isn't as good as it was in Example #2.

    2) The and() method on Specification doesn't return the same Specification instance where
    and() was called. This is counter-intuitive, and it also creates a little code clutter.
    One has to write 'specification = specification.and(usedValueGreaterThan(usedValueGreaterThan));'
    instead of simply just 'specification.and(usedValueGreaterThan(usedValueGreaterThan));'.
    Moreover, the variable 'specification' can't be immutable, which isn't necessary, of
    course, but it's often preferable.

    2) The method usedValueGreaterThan() does not have a null check, so any code that calls
    the method is responsible for performing the check, which is neither convenient nor enabling
    of code reuse.

    So, in the next example, Search Example #4, let's make things better by using the
    SpecificationFactory provided by this project.
     */
    public Specification<GuitarPedal> search_example_03(final Integer usedValueGreaterThan) {
        var specification = Specification.where(hasNotBeenSold());
        if (usedValueGreaterThan != null) {
            specification = specification.and(usedValueGreaterThan(usedValueGreaterThan));
        }
        return specification;
    }

    /*
    This example, Search Example #4, introduces use of the SpecificationFactory provided
    by this project. The methods hasNotBeenSold_usingFactory() and usedValueGreaterThan_usingFactory()
    call the SpecificationFactory instead of creating an anonymous subclass of Specification.

    The legibility of the code has been improved.

    The boilerplate code is gone, and use of the underlying JPA Criteria API is encapsulated
    and decoupled.

    The greaterThan method provides null checking of the argument 'usedValueGreaterThan' and
    handles it gracefully, returning an empty Specification ((root, query, builder) -> null)
    if the argument was null, which has no effect on the rendered SQL.

    This is a step in the right direction. But what if we have a situation where don't want to
    define each individual Specification with its own method. While this is a common practice,
    in some cases it is simply not needed and/or undesired for one reason or another.

    Let's see what we can do about that in the next 3 examples, Search Example #5, #6 and #7.
     */
    public Specification<GuitarPedal> search_example_04(final Integer usedValueGreaterThan) {
        return  Specification.where(hasNotBeenSold_usingFactory())
                .and(usedValueGreaterThan_usingFactory(usedValueGreaterThan));
    }

    public Specification<GuitarPedal> hasNotBeenSold_usingFactory() {
        return specificationFactory.isNull(GuitarPedal_.dateSold);
    }

    public Specification<GuitarPedal> usedValueGreaterThan_usingFactory(final Integer usedValueGreaterThan) {
        return specificationFactory.greaterThan(GuitarPedal_.usedValue, usedValueGreaterThan);
    }

    /*
    This example, Search Example #5, uses the SpecificationFactory in the query building
    method, rather than constructing each Specification with a separate method.

    This example has many of the same benefits as in Example #4, but there is a trade-off.
    In return for not having as many methods to maintain, the code is a little less legible.

    There is also some redundancy exposed, which may not have been as obvious earlier. It
    turns out here is no need to call Specification.where(), except to improve legibility
    or to provide null safety, because the where() method will return a Specification even
    if a null Specification is passed into it. But the SpecificationFactory methods never
    return a null Specification, so we don't need to worry about that.

    The next example, Search Example #6, shows what I mean.
     */
    public Specification<GuitarPedal> search_example_05(final Integer usedValueGreaterThan) {
        return  Specification.where(specificationFactory.isNull(GuitarPedal_.dateSold))
                .and(specificationFactory.greaterThan(GuitarPedal_.usedValue, usedValueGreaterThan));
    }

    /*
    This example, Search Example #6, uses the SpecificationFactory in the query building
    method, but since the Factory methods handle null values gracefully and always return
    a non-null Specification, we can chain them together by calling the and() method of the
    first Specification.

    However, it's still not quite as easy to read as it could be. But more importantly, there
    is still the problem where each conjunction of Specifications returns a new instance. So, if
    you had to do some kind of conditional query building other than checking for null values,
    then you must still contend with the behavior of conjunctions. It's a minor inconvenience,
    but we already have conventions to deal with such things, and this is what the next example,
    Search Example #7, demonstrates.

    NOTE: If each Specification is constructed with a distinct method, as was the case with
    Example #4, then we could do this as a nice alternative:

    public Specification<GuitarPedal> search_example_06_alt(final Integer usedValueGreaterThan) {
        return hasNotBeenSold_usingFactory()
                .and(usedValueGreaterThan_usingFactory(usedValueGreaterThan));
    }
     */
    public Specification<GuitarPedal> search_example_06(final Integer usedValueGreaterThan) {
        return specificationFactory.isNull(GuitarPedal_.dateSold)
                .and(specificationFactory.greaterThan(GuitarPedal_.usedValue, usedValueGreaterThan));
    }

    /*
    This example, Search Example #7, introduces the SpecificationBuilder. It uses a typical
    builder pattern with a Fluent API to conjoin Specifications into one.

    Under the hood, each builder method uses the corresponding methods in the SpecificationFactory,
    so it handles null values gracefully.

    The code is very legible. It is brief, to the point and concise. All boilerplate code is
    encapsulated within the SpecificationBuilder and SpecificationFactory. And there is no loss of
    legibility by choosing to define each Predicate within the builder method.

    Finally, if for whatever reason one needs to build the query conditionally, performing checks
    other than null checks, one doesn't need as much code to do that, and a builder variable, if
    defined, can be immutable.

    This concludes examples showing the benefits of using the SpecificationBuilder and
    SpecificationFactory in a variety of ways. But we haven't yet covered the benefits provided
    for performing fetches, and how to query by property values on an associated Entity.

    The next set of examples goes into the details of fetching and utilizing associations.
     */
    public Specification<GuitarPedal> search_example_07(final Integer usedValueGreaterThan) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .where().isNull(GuitarPedal_.dateSold)
                .and().isGreaterThan(GuitarPedal_.usedValue, usedValueGreaterThan)
                .toSpecification();
    }

    /*
    This example, Fetch Example #1, introduces the fetch() methods provided by the
    SpecificationBuilder to eagerly fetch associated Entities in an optimized way.

    If search_example_07() is executed with a value of 75 passed in as its argument, 2 GuitarPedals
    will be returned, given the test data used. However, 3 queries will be executed instead of one:

    select
        g1_0.id,
        g1_0.date_purchased,
        g1_0.date_sold,
        g1_0.has_stereo_output,
        g1_0.manufacturer_id,
        g1_0.name,
        g1_0.used_value
    from
        guitar_pedal g1_0
    where
        g1_0.date_sold is null
        and g1_0.used_value>?

    select
        m1_0.id,
        m1_0.name
    from
        manufacturer m1_0
    where
        m1_0.id=?

    select
        m1_0.id,
        m1_0.name
    from
        manufacturer m1_0
    where
        m1_0.id=?

    The first query returns 2 results, and for each result returned, an additional query is executed
    to get each GuitarPedal's associated Manufacturer, which is an eagerly fetched many-to-one
    association from GuitarPedal.

    Ideally there would just be 1 query instead of 3, because it's simply much better for performance,
    and we can make that happen by utilizing what the underlying JPA Criteria API provides and
    encapsulating it in the SpecificationBuilder and SpecificationFactory.

    All we need to do is call fetch() on the SpecificationBuilder (or fetch() on the
    SpecificationFactory) with the attribute representing the association we want to eagerly fetch.

    In this example, Fetch Example #1, we define a fetch of Manufacturer and the SQL is then rendered
    as 1 query instead of 3, with a join to the Manufacturer and its properties added to the select clause:

    select
        g1_0.id,
        g1_0.date_purchased,
        g1_0.date_sold,
        g1_0.has_stereo_output,
        g1_0.manufacturer_id,
        m1_0.id,
        m1_0.name,
        g1_0.name,
        g1_0.used_value
    from
        guitar_pedal g1_0
    join
        manufacturer m1_0
            on m1_0.id=g1_0.manufacturer_id
    where
        1=1

    The 1=1 in thw where clause is an artifact of a trick the SpecificationFactory does in order
    to construct a Specification that defines a fetch.

    But what if we want to filter by properties belonging to the associated Entity?

    The next example, Fetch Example #2, begins looking at how to do that.
     */
    public Specification<GuitarPedal> fetch_example_01() {
        return SpecificationBuilder.from(GuitarPedal.class)
                .fetch(GuitarPedal_.manufacturer)
                .toSpecification();
    }

    /*
    This example, Fetch Example #2, uses the fetch() method provided by the
    SpecificationBuilder to eagerly fetch an associated Entity, but it also uses
    the where() method to filter by one of the associated Entity's properties.

    At this time, this project does not provide any builder or factory methods
    to filter by properties belonging to an associated Entity, but it is on the
    roadmap. In the meantime, one can use the where() to pass in a an anonymous
    subclass of Specification that defines the filtering.

    Here we use a method from the SpecificationUtil Class (which is used by many
    methods in SpecificationFactory) to perform a null check. This is the noneAreNull()
    method. It accepts any number of Objects and simply checks to see if any are null,
    returning true only if none of its arguments are null.

    If the argument 'name' is null, then null will be returned by the lambda expression,
    and it will have no effect on the rendered SQL query. Otherwise, a Criteria Predicate
    is built and returned.

    NOTE: Calling fetch() is not required to do the filtering. They are independent
    operations. We call fetch() simply to limit the number of queries.

    The SQL renders as:

    select
        g1_0.id,
        g1_0.date_purchased,
        g1_0.date_sold,
        g1_0.has_stereo_output,
        g1_0.manufacturer_id,
        m1_0.id,
        m1_0.name,
        g1_0.name,
        g1_0.used_value
    from
        guitar_pedal g1_0
    join
        manufacturer m1_0
            on m1_0.id=g1_0.manufacturer_id
    join
        manufacturer m2_0
            on m2_0.id=g1_0.manufacturer_id
    where
        1=1
        and m2_0.name=?

    Yes, there are 2 joins with the manufacturer table. One is in support of the
    eager fetch while the other is for the filter. This isn't ideal, and there is
    a way to make it have only one join, but I'm hesitant to recommend doing that,
    because if applying that pattern to on associated Collection of Entities, you
    may get unexpected results (see Fetch Example #7).

    The next example, Fetch Example #3, begins looking at fetching and filtering
    associated Collections of Entities.
     */
    public Specification<GuitarPedal> fetch_example_02(final String name) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .fetch(GuitarPedal_.manufacturer)
                .where(
                        (root, query, builder) -> {
                            if (SpecificationUtil.noneAreNull(name)) {
                                final var join = root.join(GuitarPedal_.manufacturer);
                                return builder.equal(join.get(Manufacturer_.name), name);
                            }
                            return null;
                        }
                )
                .toSpecification();
    }

    /*
    This example, Fetch Example #3, uses the fetch() method provided by the
    SpecificationBuilder twice, one to eagerly fetch an associated Entity and
    another to eagerly fetch an associated Collection of Entities. It also uses
    the where() method to filter by a property belonging to Entity of the
    associated Collection.

    Filtering by a property of an Entity in a Collection is not much different
    than filtering by just an associated Entity. But since it is a Collection,
    there are behavioral details to be aware of.

    This example essentially finds all GuitarPedals that are tagged with any of
    the values given in the argument 'tags' and returns them with their associated
    Manufacturer and all of their associated GuitarPedalTags eagerly fetched with
    one query. The filter is NOT filtering the Collection. It is filtering the Root
    Entity by an association with another Entity, and for each matching Root Entity
    instance the entire associated Collection is fetched and returned.

    In the next example, Fetch Example #4, we'll clean this up a bit.
     */
    public Specification<GuitarPedal> fetch_example_03(final Collection<String> tags) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .fetch(GuitarPedal_.manufacturer)
                .fetch(GuitarPedal_.tags)
                .where(
                        (root, query, builder) -> {
                            if (SpecificationUtil.noneAreNull(tags)) {
                                final var join = root.join(GuitarPedal_.tags);
                                return join.get(GuitarPedalTag_.tag).in(tags);
                            }
                            return null;
                        }
                )
                .toSpecification();
    }

    /*
    This example, Fetch Example #4, does exactly the same thing as Example #3, but the
    Specification to filter by the associated Collection of Entities is refactored into
    a method of its own, which makes things a little easier to read.

    In the tagsContain() method, the null handling is done a little differently than in
    Example #3. In this case, the anonymous subclass is wrapped in the null check, and
    if the argument 'tags' is null, the ghost() method in the SpecificationFactory is
    called, which simply returns a Specification whose toPredicate() method (the lambda
    expression) returns null.

    Now what if we need to filter the associated Entity with more than one Specification?
    How can we do that?

    Let's look at the next example, Fetch Example #5, and see what NOT to do.
     */
    public Specification<GuitarPedal> fetch_example_04(final Collection<String> tags) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .fetch(GuitarPedal_.manufacturer)
                .fetch(GuitarPedal_.tags)
                .where(tagsContain(tags))
                .toSpecification();
    }

    public Specification<GuitarPedal> tagsContain(final Collection<String> tags) {
        if (SpecificationUtil.noneAreNull(tags)) {
            return (root, query, builder) -> {
                final var join = root.join(GuitarPedal_.tags);
                return join.get(GuitarPedalTag_.tag).in(tags);
            };
        }
        return specificationFactory.ghost();
    }

    /*
    This example, Fetch Example #5, does exactly the same thing as Example #3, but it
    has an additional Specification to filter by the associated Collection of Entities.

    Each Specification to filter by the associated Collection is created and added
    to the builder independently of each other. On the surface this may look like fine,
    but it has a negative effect on the behavior of the eager fetch of the associated
    Collection.

    Given the state of the test data, if this method is called with an id of '300' and
    a Collection containing just one String of 'shoegaze', then 1 Guitar Pedal will be
    returned, but it will be returned with a Collection containing 16 GuitarPedalTags
    when it should only contain 4.

    This is an artifact of the fetch and the two filters each creating a join in the SQL
    query, and the effect is that the entire Collection is populated 1 time for every
    Collection item.

    In the next example, Fetch Example #6, we'll take a different approach that provides
    the expected results.
     */
    public Specification<GuitarPedal> fetch_example_05(final Long id,
                                                       final Collection<String> tags) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .fetch(GuitarPedal_.manufacturer)
                .fetch(GuitarPedal_.tags)
                .where(
                        (root, query, builder) -> {
                            if (SpecificationUtil.noneAreNull(id)) {
                                final var join = root.join(GuitarPedal_.tags);
                                return builder.greaterThanOrEqualTo(join.get(GuitarPedalTag_.id), id);
                            }
                            return null;
                        }
                )
                .and(
                        (root, query, builder) -> {
                            if (SpecificationUtil.noneAreNull(tags)) {
                                final var join = root.join(GuitarPedal_.tags);
                                return join.get(GuitarPedalTag_.tag).in(tags);
                            }
                            return null;
                        }
                )
                .toSpecification();
    }

    /*
    This example, Fetch Example #6, does what Example #5 tries to do, but this example
    does it successfully.

    The Specifications that filter by the associated Collection of Entities are created
    using the same Join instance and merged before being added to the builder. By using
    the same Join, only two joins will be rendered in the SQL query (again, one for the
    fetch, another for the filtering), and the query returns the expected results.

    Note that both Specifications are wrapped by an additional set of null checks, so
    that a Join is only instantiated if one or both of the arguments are non-null.

    Given the state of the test data, if this method is called with an id of '300' and
    a Collection containing just one String of 'shoegaze', then 1 Guitar Pedal will be
    returned with a Collection containing 4 GuitarPedalTags, as expected, instead of the
    16 that were returned in Example #5.

    In the next example, Fetch Example #7, let's look at a question raised in Example #2,
    how to make the eager fetch and filtering use the same join, and why in most cases it's
    undesirable when working with associated Collections.
     */
    public Specification<GuitarPedal> fetch_example_06(final Long id,
                                                       final Collection<String> tags) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .fetch(GuitarPedal_.manufacturer)
                .fetch(GuitarPedal_.tags)
                .where(
                        (root, query, builder) -> {
                            if (SpecificationUtil.noneAreNull(id)
                                    || SpecificationUtil.noneAreNull(tags)) {
                                final var join = root.join(GuitarPedal_.tags);
                                final Specification<GuitarPedal> idGreaterThan = (r1, q1, b1) -> {
                                    if (SpecificationUtil.noneAreNull(id)) {
                                        return b1.greaterThanOrEqualTo(join.get(GuitarPedalTag_.id), id);
                                    }
                                    return null;
                                };
                                final Specification<GuitarPedal> tagsContain = (r2, q2, b2) -> {
                                    if (SpecificationUtil.noneAreNull(tags)) {
                                        return join.get(GuitarPedalTag_.tag).in(tags);
                                    }
                                    return null;
                                };
                                return idGreaterThan.and(tagsContain).toPredicate(root, query, builder);
                            }
                            return null;
                        }
                )
                .toSpecification();
    }

    /*
    This example, Fetch Example #7, renders just one join to the tags table, using the
    same Join instance for both the eager fetch and the Specifications that filter by
    the associated Collection.

    Unlike Example #6, in this example we create a join by calling root.fetch() rather
    than calling root.join(). The fetch() method doesn't return a Join, but the Object
    is castable to a Join, which is unfortunately recognized as an unchecked cast.

    Since we're using fetch() to render a join for both the eager fetching and the
    filtering, there is no need to for an additional null check like there was in
    Example #6.

    The SQL renders as:

    select
        g1_0.id,
        g1_0.date_purchased,
        g1_0.date_sold,
        g1_0.has_stereo_output,
        g1_0.manufacturer_id,
        m1_0.id,
        m1_0.name,
        g1_0.name,
        t1_0.guitar_pedal_id,
        t1_0.id,
        t1_0.tag,
        g1_0.used_value
    from
        guitar_pedal g1_0
    join
        manufacturer m1_0
            on m1_0.id=g1_0.manufacturer_id
    join
        guitar_pedal_tag t1_0
            on g1_0.id=t1_0.guitar_pedal_id
    where
        1=1
        and t1_0.id>=?
        and t1_0.tag in (?)

    It creates only one join, sure enough, which would be great if the association was
    an Entity rather than a Collection of Entities. But for an associated Collection,
    it doesn't give us what we would typically want.

    When we use fetch() to eagerly fetch an associated Collections, most of the time we
    will want the whole Collection returned, since we recognize that it is best practice
    when working with an Aggregate, but this query fails to provide the whole Collection.

    Given the state of the test data, if this method is called with an id of '300' and
    a Collection containing just one String of 'shoegaze', then 1 Guitar Pedal will be
    returned with a Collection containing only 1 GuitarPedalTag (the one for 'shoegaze'),
    instead of the 4 we would typically expect.

    The reason for this is obvious. It's because there's only one join for the association.

    Consequently, one must be careful if taking this approach. I find it much simpler to
    observe a general practice of having one join for fetching and another for filtering.
    It's consistent and doesn't appear to carry any significant drawbacks. But if you're
    looking to do some query optimization, this pattern of using only one join may help
    with that, depending on the case, so it's good to be aware of.
     */
    @SuppressWarnings("unchecked")
    public Specification<GuitarPedal> fetch_example_07(final Long id,
                                                       final Collection<String> tags) {
        return SpecificationBuilder.from(GuitarPedal.class)
                .fetch(GuitarPedal_.manufacturer)
                .where(
                        (root, query, builder) -> {
                            final var join = (Join<GuitarPedal, GuitarPedalTag>) root.fetch(GuitarPedal_.tags);
                            final Specification<GuitarPedal> idGreaterThan = (r1, q1, b1) -> {
                                if (SpecificationUtil.noneAreNull(id)) {
                                    return b1.greaterThanOrEqualTo(join.get(GuitarPedalTag_.id), id);
                                }
                                return null;
                            };
                            final Specification<GuitarPedal> tagsContain = (r2, q2, b2) -> {
                                if (SpecificationUtil.noneAreNull(tags)) {
                                    return join.get(GuitarPedalTag_.tag).in(tags);
                                }
                                return null;
                            };
                            return idGreaterThan.and(tagsContain).toPredicate(root, query, builder);
                        }
                )
                .toSpecification();
    }
}

