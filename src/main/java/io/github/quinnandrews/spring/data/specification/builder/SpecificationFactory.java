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

    public SpecificationFactory() {
        // no-op
    }

    public <T> Specification<T> equal(final SingularAttribute<T, ?> attribute,
                                      final Object value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.equal(root.get(attribute), value);
        }
        return ghost();
    }

    public <T> Specification<T> notEqual(final SingularAttribute<T, ?> attribute,
                                         final Object value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.notEqual(root.get(attribute), value);
        }
        return ghost();
    }

    public <T> Specification<T> like(final SingularAttribute<T, String> attribute,
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

    public <T> Specification<T> notLike(final SingularAttribute<T, String> attribute,
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

    public <T> Specification<T> equalOrLike(final SingularAttribute<T, String> attribute,
                                            final String value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return isWildcardExpression(value) ? like(attribute, value) : equal(attribute, value);
    }

    public <T> Specification<T> isNull(final SingularAttribute<T, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isNull(root.get(attribute));
    }

    public <T> Specification<T> isNotNull(final SingularAttribute<T, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isNotNull(root.get(attribute));
    }

    public <T> Specification<T> isTrue(final SingularAttribute<T, Boolean> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isTrue(root.get(attribute).as(Boolean.class));
    }

    public <T> Specification<T> isFalse(final SingularAttribute<T, Boolean> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return (root, query, builder) -> builder.isFalse(root.get(attribute).as(Boolean.class));
    }

    public  <T, V extends Comparable<? super V>> Specification<T> greaterThan(final SingularAttribute<T, V> attribute,
                                                                              final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.greaterThan(root.get(attribute), value);
        }
        return ghost();
    }

    public <T, V extends Comparable<? super V>> Specification<T> greaterThanOrEqualTo(final SingularAttribute<T, V> attribute,
                                                                                      final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(attribute), value);
        }
        return ghost();
    }

    public <T, V extends Comparable<? super V>> Specification<T> lessThan(final SingularAttribute<T, V> attribute,
                                                                          final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.lessThan(root.get(attribute), value);
        }
        return ghost();
    }

    public <T, V extends Comparable<? super V>> Specification<T> lessThanOrEqualTo(final SingularAttribute<T, V> attribute,
                                                                                   final V value) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(value)) {
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get(attribute), value);
        }
        return ghost();
    }

    public <T, V extends Comparable<? super V>> Specification<T> between(final SingularAttribute<T, V> attribute,
                                                                         final V firstValue,
                                                                         final V secondValue) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(firstValue, secondValue)) {
            return (root, query, builder) -> builder.between(root.get(attribute), firstValue, secondValue);
        }
        return ghost();
    }

    public <T> Specification<T> in(final SingularAttribute<T, ?> attribute,
                                   final Collection<?> collection) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(collection) && !collection.isEmpty()) {
            return (root, query, builder) -> root.get(attribute).in(collection);
        }
        return ghost();
    }

    public <T> Specification<T> in(final SingularAttribute<T, ?> attribute,
                                   final Object... values) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        return in(attribute, Arrays.stream(values)
                .filter(SpecificationUtil::noneAreNull)
                .collect(Collectors.toSet()));
    }

    public <T> Specification<T> fetch(final SingularAttribute<T, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(attribute)) {
            return (root, query, builder) -> {
                root.fetch(attribute, JoinType.INNER);
                return builder.conjunction();
            };
        }
        return ghost();
    }

    public <T> Specification<T> fetch(final PluralAttribute<T, ?, ?> attribute) {
        Objects.requireNonNull(attribute, ATTRIBUTE_CANNOT_BE_NULL);
        if (noneAreNull(attribute)) {
            return (root, query, builder) -> {
                root.fetch(attribute, JoinType.INNER);
                return builder.conjunction();
            };
        }
        return ghost();
    }

    public <T> Specification<T> ghost() {
        return (root, query, builder) -> null;
    }
}
