package io.github.quinnandrews.spring.data.specification.builder;

import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.Objects;

/**
 * Generates and composes Specifications with a fluent-API that is easy to read.
 *
 * @param <T> The Entity Type to query from as the Aggregate Root.
 *
 * @author Quinn Andrews
 */
public class SpecificationBuilder<T> {
    
    private Specification<T> specification;

    /**
     * Default Constructor. Private since this Class is meant
     * to be instantiated with the from(final Class<T> root)
     * method.
     */
    private SpecificationBuilder() {
        // no-op
    }

    /**
     * Returns a new instance of SpecificationBuilder with the
     * given root as the Aggregate Root of the Specification.
     *
     * @param root The Entity Class to query from as the
     *             Aggregate Root.
     * @return A new instance of SpecificationBuilder
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given root is null.
     */
    public static <T> SpecificationBuilder<T> from(final Class<T> root) {
        Objects.requireNonNull(root, "Argument 'root' cannot be null.");
        return new SpecificationBuilder<>();
    }

    /**
     * Returns the underlying composite Specification in
     * its current state. WARNING: Can be null under some
     * circumstances.
     *
     * @return The underlying composite Specification that
     *         represents the result of the build.
     */
    public Specification<T> toSpecification() {
        return specification;
    }

    /**
     * Simply returns the current instance of the
     * SpecificationBuilder. Used to maintain fluency
     * of the code, to better resemble SQL and the
     * way the underlying query is spoken.
     *
     * @return The current instance of the SpecificationBuilder.
     */
    public SpecificationBuilder<T> where() {
        return this;
    }

    /**
     * Adds the given Specification to the current Specification
     * with a conjunction (unless no Specification has yet been
     * added) using the language of 'where'.
     *
     * @param specification The Specification to add the current
     *                      Specification.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given Specification is null.
     */
    public SpecificationBuilder<T> where(final Specification<T> specification) {
        Objects.requireNonNull(specification, "Argument 'specification' cannot be null.");
        this.specification = this.specification == null ?
                Specification.where(specification) : this.specification.and(specification);
        return this;
    }

    /**
     * Simply returns the current instance of the
     * SpecificationBuilder. Used to maintain fluency
     * of the code, to better resemble SQL and the
     * way the underlying query is spoken.
     *
     * @return The current instance of the SpecificationBuilder.
     */
    public SpecificationBuilder<T> and() {
        return this;
    }

    /**
     * Adds the given Specification to the current Specification
     * with a conjunction (unless no Specification has yet been
     * added) using the language of 'and'.
     *
     * @param specification The Specification to add the current
     *                      Specification.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given Specification is null.
     */
    public SpecificationBuilder<T> and(final Specification<T> specification) {
        return where(specification);
    }

    /**
     * Adds the given Specification to the current Specification
     * with a disjunction (unless no Specification has yet been
     * added) using the language of 'or'.
     *
     * @param specification The Specification to add the current
     *                      Specification.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given Specification is null.
     */
    public SpecificationBuilder<T> or(final Specification<T> specification) {
        Objects.requireNonNull(specification, "Argument 'specification' cannot be null.");
        this.specification = this.specification == null ?
                Specification.where(specification) : this.specification.or(specification);
        return this;
    }

    /**
     * Simply returns the current instance of the
     * SpecificationBuilder. Used to maintain fluency
     * of the code, to better resemble the way the
     * underlying query is spoken.
     *
     * @return The current instance of the SpecificationBuilder.
     */
    public SpecificationBuilder<T> with() {
        return this;
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL equals clause, or a no-op "ghost" Predicate if the
     * given value is null, to the current Specification.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isEqualTo(final SingularAttribute<T, ?> attribute,
                                             final Object value) {
        return where(SpecificationFactory.isEqualTo(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL not equals clause, or a no-op "ghost" Predicate if
     * the given value is null, to the current Specification.
     *
     * @param attribute The attribute to check against the value.
     * @param value The value to check against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isNotEqualTo(final SingularAttribute<T, ?> attribute,
                                                final Object value) {
        return where(SpecificationFactory.isNotEqualTo(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL like clause, or a no-op "ghost" Predicate if the
     * given value is null, to the current Specification.
     * Matching is case-insensitive.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isLike(final SingularAttribute<T, String> attribute,
                                          final String value) {
        return where(SpecificationFactory.isLike(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL not like clause, or a no-op "ghost" Predicate if the
     * given value is null, to the current Specification.
     * Matching is case-insensitive.
     *
     * @param attribute The attribute to check against the value.
     * @param value The value to check against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isNotLike(final SingularAttribute<T, String> attribute,
                                             final String value) {
        return where(SpecificationFactory.isNotLike(attribute, value));
    }

    /**
     * If the value contains one or more SQL wildcard characters,
     * adds a Specification with a Predicate representing an
     * SQL like clause to the current Specification. Otherwise,
     * if the value does not contain any SQL wildcard characters,
     * adds a Specification with a Predicate representing an SQL
     * equals clause to the current Specification. Adds a
     * Specification with a no-op "ghost" Predicate if the given
     * value is null. Like matching is case-insensitive.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isEqualToOrLike(final SingularAttribute<T, String> attribute,
                                                   final String value) {
        return where(SpecificationFactory.isEqualToOrLike(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL is null clause to the current Specification.
     *
     * @param attribute The attribute to check.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isNull(final SingularAttribute<T, ?> attribute) {
        return where(SpecificationFactory.isNull(attribute));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL is not null clause to the current Specification.
     *
     * @param attribute The attribute to check.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isNotNull(final SingularAttribute<T, ?> attribute) {
        return where(SpecificationFactory.isNotNull(attribute));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL equals clause that checks if the given boolean attribute
     * is true to the current Specification.
     *
     * @param attribute The attribute to check.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isTrue(final SingularAttribute<T, Boolean> attribute) {
        return where(SpecificationFactory.isTrue(attribute));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL equals clause that checks if the given boolean attribute
     * is false to the current Specification.
     *
     * @param attribute The attribute to check.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isFalse(final SingularAttribute<T, Boolean> attribute) {
        return where(SpecificationFactory.isFalse(attribute));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL greater than clause, or a no-op "ghost" Predicate if
     * the given value is null, to the current Specification.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public <V extends Comparable<? super V>> SpecificationBuilder<T> isGreaterThan(final SingularAttribute<T, V> attribute,
                                                                                   final V value) {
        return where(SpecificationFactory.isGreaterThan(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL greater than or equal to clause, or a no-op "ghost"
     * Predicate if the given value is null, to the current
     * Specification.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public <V extends Comparable<? super V>>  SpecificationBuilder<T> isGreaterThanOrEqualTo(final SingularAttribute<T, V> attribute,
                                                                                             final V value) {
        return where(SpecificationFactory.isGreaterThanOrEqualTo(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL less than clause, or a no-op "ghost" Predicate if
     * the given value is null, to the current Specification.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public <V extends Comparable<? super V>> SpecificationBuilder<T> isLessThan(final SingularAttribute<T, V> attribute,
                                                                                final V value) {
        return where(SpecificationFactory.isLessThan(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL less than or equal to clause, or a no-op "ghost"
     * Predicate if the given value is null, to the current
     * Specification.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public <V extends Comparable<? super V>> SpecificationBuilder<T> isLessThanOrEqualTo(final SingularAttribute<T, V> attribute,
                                                                                         final V value) {
        return where(SpecificationFactory.isLessThanOrEqualTo(attribute, value));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL between clause, or a no-op "ghost" Predicate if either
     * of the given values are null, to the current Specification.
     *
     * @param attribute The attribute to match against the value.
     * @param firstValue The value to match against the attribute
     *                   as the inclusive first half of the between
     *                   clause.
     * @param secondValue The value to match against the attribute
     *                    as the exclusive second half of the between
     *                    clause.
     * @return The current instance of the SpecificationBuilder.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public <V extends Comparable<? super V>> SpecificationBuilder<T> isBetween(final SingularAttribute<T, V> attribute,
                                                                               final V firstValue,
                                                                               final V secondValue) {
        return where(SpecificationFactory.isBetween(attribute, firstValue, secondValue));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL in clause, or a no-op "ghost" Predicate if the given
     * collection is null, to the current Specification.
     *
     * @param attribute The attribute to match against the values.
     * @param collection The collection of values to match against
     *                   the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isIn(final SingularAttribute<T, ?> attribute,
                                        final Collection<?> collection) {
        return where(SpecificationFactory.isIn(attribute, collection));
    }

    /**
     * Adds a Specification with a Predicate representing an
     * SQL in clause, or a no-op "ghost" Predicate if the given
     * collection is null, to the current Specification.
     *
     * @param attribute The attribute to match against the values.
     * @param values The values to match against the attribute.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given attribute is null.
     */
    public SpecificationBuilder<T> isIn(final SingularAttribute<T, ?> attribute,
                                        final Object... values) {
        return where(SpecificationFactory.isIn(attribute, values));
    }

    /**
     * Defines a join with the given singular association
     * in order to fetch it eagerly as part of the SQL query.
     * A useful optimization technique to fetch an entire
     * Aggregate with one query instead of many.
     *
     * @param attribute The singular association to fetch.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given association is null.
     */
    public SpecificationBuilder<T> fetchOf(final SingularAttribute<T, ?> attribute) {
        return and(SpecificationFactory.fetchOf(attribute));
    }

    /**
     * Defines a join with the given collection association
     * in order to fetch it eagerly as part of the SQL query.
     * A useful optimization technique to fetch an entire
     * Aggregate with one query instead of many.
     *
     * @param attribute The collection association to fetch.
     * @return The current instance of the SpecificationBuilder.
     * @throws NullPointerException if the given association is null.
     */
    public SpecificationBuilder<T> fetchOf(final PluralAttribute<T, ?, ?> attribute) {
        return and(SpecificationFactory.fetchOf(attribute));
    }
}
