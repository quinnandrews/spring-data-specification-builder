package io.github.quinnandrews.spring.data.specification.builder;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static io.github.quinnandrews.spring.data.specification.builder.SpecificationUtil.*;

/**
 * Generates Specifications with null-safe handling of Attribute values.
 *
 * @author Quinn Andrews
 */
public class SpecificationFactory {

    private static final String ATTRIBUTE_CANNOT_BE_NULL = "Argument 'attribute' cannot be null.";

    /**
     * Default Constructor. Private since this Class is not
     * meant to be instantiated.
     */
    private SpecificationFactory() {
        // no-op
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL equals clause, or a no-op "ghost" Predicate if the given
     * value is null.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL equals clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isEqualTo(final SingularAttribute<T, ?> attribute,
                                                 final Object value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.equal(root.get(attribute), value);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL not equals clause, or a no-op "ghost" Predicate if the
     * given value is null.
     *
     * @param attribute The attribute to check against the value.
     * @param value The value to check against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL equals clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isNotEqualTo(final SingularAttribute<T, ?> attribute,
                                                    final Object value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.notEqual(root.get(attribute), value);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL like clause, or a no-op "ghost" Predicate if the given
     * value is null. Matching is case-insensitive.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL like clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isLike(final SingularAttribute<T, String> attribute,
                                              final String value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        final Object trimmedValue = stripToNull(value);
        if (noneAreNull(trimmedValue)) {
            return (root, query, builder) -> builder.like(
                    builder.lower(root.get(attribute).as(String.class)),
                    toLowerCase(trimmedValue.toString())
            );
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL not like clause, or a no-op "ghost" Predicate if the
     * given value is null. Matching is case-insensitive.
     *
     * @param attribute The attribute to check against the value.
     * @param value The value to check against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL not like clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isNotLike(final SingularAttribute<T, String> attribute,
                                                 final String value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        final Object trimmedValue = stripToNull(value);
        if (noneAreNull(trimmedValue)) {
            return (root, query, builder) -> builder.notLike(
                    builder.lower(root.get(attribute).as(String.class)),
                    toLowerCase(trimmedValue.toString())
            );
        }
        return ghost();
    }

    /**
     * If the value contains one or more SQL wildcard characters,
     * returns a Specification with a Predicate representing an
     * SQL like clause. Otherwise, if the value does not contain
     * any SQL wildcard characters, returns a Specification with
     * a Predicate representing an SQL equals clause. Returns a
     * Specification with a no-op "ghost" Predicate if the given
     * value is null. Like matching is case-insensitive.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL like clause or equals clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isEqualToOrLike(final SingularAttribute<T, String> attribute,
                                                       final String value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return isWildcardExpression(value) ? isLike(attribute, value) : isEqualTo(attribute, value);
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL is null clause.
     *
     * @param attribute The attribute to check.
     * @return A Specification with a Predicate that defines an
     *         SQL is null clause.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isNull(final SingularAttribute<T, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isNull(root.get(attribute));
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL is not null clause.
     *
     * @param attribute The attribute to check.
     * @return A Specification with a Predicate that defines an
     *         SQL is not null clause.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isNotNull(final SingularAttribute<T, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isNotNull(root.get(attribute));
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL equals clause that checks if the given boolean attribute
     * is true.
     *
     * @param attribute The attribute to check.
     * @return A Specification with a Predicate that defines an
     *         SQL equals clause that checks if the given boolean
     *         attribute is true.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isTrue(final SingularAttribute<T, Boolean> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isTrue(root.get(attribute).as(Boolean.class));
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL equals clause that checks if the given boolean attribute
     * is false.
     *
     * @param attribute The attribute to check.
     * @return A Specification with a Predicate that defines an
     *         SQL equals clause that checks if the given boolean
     *         attribute is false.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isFalse(final SingularAttribute<T, Boolean> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isFalse(root.get(attribute).as(Boolean.class));
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL greater than clause, or a no-op "ghost" Predicate if
     * the given value is null.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL greater than clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T, V extends Comparable<? super V>> Specification<T> isGreaterThan(final SingularAttribute<T, V> attribute,
                                                                                      final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.greaterThan(root.get(attribute), value);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL greater than or equal to clause, or a no-op "ghost"
     * Predicate if the given value is null.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL greater than or equal to clause, or a no-op
     *         Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T, V extends Comparable<? super V>> Specification<T> isGreaterThanOrEqualTo(final SingularAttribute<T, V> attribute,
                                                                                               final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(attribute), value);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL less than clause, or a no-op "ghost" Predicate if
     * the given value is null.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL less than clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T, V extends Comparable<? super V>> Specification<T> isLessThan(final SingularAttribute<T, V> attribute,
                                                                                   final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.lessThan(root.get(attribute), value);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL less than or equal to clause, or a no-op "ghost"
     * Predicate if the given value is null.
     *
     * @param attribute The attribute to match against the value.
     * @param value The value to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL less than or equal to clause, or a no-op
     *         Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T, V extends Comparable<? super V>> Specification<T> isLessThanOrEqualTo(final SingularAttribute<T, V> attribute,
                                                                                            final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(attribute), value);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL between clause, or a no-op "ghost" Predicate if either
     * of the given values are null.
     *
     * @param attribute The attribute to match against the value.
     * @param firstValue The value to match against the attribute
     *                   as the inclusive first half of the between
     *                   clause.
     * @param secondValue The value to match against the attribute
     *                    as the exclusive second half of the between
     *                    clause.
     * @return A Specification with a Predicate that defines an
     *         SQL between clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @param <V> The type assigned to the attribute value.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T, V extends Comparable<? super V>> Specification<T> isBetween(final SingularAttribute<T, V> attribute,
                                                                                  final V firstValue,
                                                                                  final V secondValue) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(firstValue, secondValue)) {
            return (root, query, builder) -> builder.between(root.get(attribute), firstValue, secondValue);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL in clause, or a no-op "ghost" Predicate if the given
     * collection is null.
     *
     * @param attribute The attribute to match against the values.
     * @param collection The collection of values to match against
     *                   the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL in clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isIn(final SingularAttribute<T, ?> attribute,
                                            final Collection<?> collection) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(collection) && !collection.isEmpty()) {
            return (root, query, builder) -> root.get(attribute).in(collection);
        }
        return ghost();
    }

    /**
     * Returns a Specification with a Predicate representing an
     * SQL in clause, or a no-op "ghost" Predicate if the given
     * values are null.
     *
     * @param attribute The attribute to match against the values.
     * @param values The values to match against the attribute.
     * @return A Specification with a Predicate that defines an
     *         SQL in clause, or a no-op Predicate.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given attribute is null.
     */
    public static <T> Specification<T> isIn(final SingularAttribute<T, ?> attribute,
                                            final Object... values) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return isIn(attribute, Arrays.stream(values)
                .filter(SpecificationUtil::noneAreNull)
                .collect(Collectors.toSet()));
    }

    /**
     * Defines a join with the given singular association
     * in order to fetch it eagerly as part of the SQL query.
     * A useful optimization technique to fetch an entire
     * Aggregate with one query instead of many.
     *
     * @param attribute The singular association to fetch.
     * @return A Specification with a Predicate that defines
     *         an eager fetch of the given association.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given association is null.
     */
    public static <T> Specification<T> fetchOf(final SingularAttribute<T, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> {
            root.fetch(attribute, JoinType.INNER);
            return builder.conjunction();
        };
    }

    /**
     * Defines a join with the given collection association
     * in order to fetch it eagerly as part of the SQL query.
     * A useful optimization technique to fetch an entire
     * Aggregate with one query instead of many.
     *
     * @param attribute The collection association to fetch.
     * @return A Specification with a Predicate that defines
     *         an eager fetch of the given association.
     * @param <T> The Aggregate Root of the Specification.
     * @throws NullPointerException if the given association is null.
     */
    public static <T> Specification<T> fetchOf(final PluralAttribute<T, ?, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> {
            root.fetch(attribute, JoinType.INNER);
            return builder.conjunction();
        };
    }

    /**
     * Returns a Specification that returns a null Predicate.
     * Essentially a no-op. Convenient when composing
     * Specifications, so that a Specification will not
     * be null.
     *
     * @return A Specification with a null Predicate.
     * @param <T> The Aggregate Root of the Specification.
     */
    public static <T> Specification<T> ghost() {
        return (root, query, builder) -> null;
    }
}
