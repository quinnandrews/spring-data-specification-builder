package io.github.quinnandrews.spring.data.specification.builder;

import io.github.quinnandrews.spring.data.specification.builder.application.Application;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.junit.jupiter.api.Test;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
class SpecificationBuilderTest {

    @Test
    @Deprecated
    void withRoot_returnsNewInstance() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class);
        assertNotNull(builder);
        assertNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void withRoot_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(null)
        );
    }

    @Test
    void from_returnsNewInstance() {
        var builder = SpecificationBuilder.from(GuitarPedal.class);
        assertNotNull(builder);
        assertNull(builder.toSpecification());
    }

    @Test
    void from_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(null)
        );
    }

    @Test
    void toSpecification_returnsSpecification_ifAnyDefined() {
        assertNotNull(SpecificationBuilder.from(GuitarPedal.class)
                .where().isEqualTo(GuitarPedal_.id, 0L)
                .toSpecification());
    }

    @Test
    void toSpecification_returnsNull_whenNoSpecificationsDefined() {
        assertNull(SpecificationBuilder.from(GuitarPedal.class)
                .toSpecification());
    }

    @Test
    void where_returnsBuilder() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where();
        assertNotNull(builder);
    }

    @Test
    void where_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where(SpecificationFactory.ghost());
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void where_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where(null)
        );
    }

    @Test
    void and_returnsBuilder() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .and();
        assertNotNull(builder);
    }

    @Test
    void and_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .and(SpecificationFactory.ghost());
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void and_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .and(null)
        );
    }

    @Test
    void or_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .or(SpecificationFactory.ghost());
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void or_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .or(null)
        );
    }

    @Test
    void with_returnsBuilder() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .with();
        assertNotNull(builder);
    }

    @Test
    @Deprecated
    void andWhere_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .andWhere(SpecificationFactory.ghost());
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void andWhere_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .andWhere(null)
        );
    }

    @Test
    @Deprecated
    void orWhere_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .orWhere(SpecificationFactory.ghost());
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void orWhere_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .orWhere(null)
        );
    }

    @Test
    @Deprecated
    void whereEquals_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEquals(GuitarPedal_.id, 0L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereEquals_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEquals(GuitarPedal_.id, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereEquals_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereEquals(null, 0L)
        );
    }

    @Test
    @Deprecated
    void whereNotEquals_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotEquals(GuitarPedal_.id, 0L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereNotEquals_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotEquals(GuitarPedal_.id, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereNotEquals_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereNotEquals(null, 0L)
        );
    }

    @Test
    @Deprecated
    void whereLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLike(null, "%test%")
        );
    }

    @Test
    @Deprecated
    void whereNotLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereNotLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereNotLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereNotLike(null, "%test%")
        );
    }

    @Test
    @Deprecated
    void whereEqualsOrLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEqualsOrLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereEqualsOrLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEqualsOrLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereEqualsOrLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereEqualsOrLike(null, "%test%")
        );
    }

    @Test
    @Deprecated
    void whereIsTrue_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsTrue(GuitarPedal_.hasStereoOutput);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIsTrue_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsTrue(null)
        );
    }

    @Test
    @Deprecated
    void whereIsFalse_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsFalse(GuitarPedal_.hasStereoOutput);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIsFalse_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsFalse(null)
        );
    }

    @Test
    @Deprecated
    void whereIsNull_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsNull(GuitarPedal_.dateSold);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIsNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsNull(null)
        );
    }

    @Test
    @Deprecated
    void whereIsNotNull_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsNotNull(GuitarPedal_.dateSold);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIsNotNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsNotNull(null)
        );
    }

    @Test
    @Deprecated
    void whereGreaterThan_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThan(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereGreaterThan_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThan(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereGreaterThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereGreaterThan(null, 0)
        );
    }

    @Test
    @Deprecated
    void whereGreaterThanOrEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereGreaterThanOrEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereGreaterThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereGreaterThanOrEqualTo(null, 0)
        );
    }

    @Test
    @Deprecated
    void whereLessThan_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThan(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereLessThan_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThan(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereLessThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLessThan(null, 0)
        );
    }

    @Test
    @Deprecated
    void whereLessThanOrEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereLessThanOrEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereLessThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLessThanOrEqualTo(null, 0)
        );
    }

    @Test
    @Deprecated
    void whereBetween_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereBetween(
                        GuitarPedal_.datePurchased,
                        LocalDate.now(),
                        LocalDate.now().plusDays(7)
                );
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereBetween_returnsBuilderWithSpecification_evenWhenEitherValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereBetween(GuitarPedal_.usedValue, null, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());

        builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereBetween(GuitarPedal_.usedValue, 0, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());

        builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereBetween(GuitarPedal_.usedValue, null, 9);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereBetween_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereBetween(null, 0L, 9L)
        );
    }

    @Test
    @Deprecated
    void whereIn_collection_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, List.of(1L, 2L, 3L));
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIn_collection_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, (Collection<?>) null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIn_collection_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIn(null, List.of(1L, 2L, 3L))
        );
    }

    @Test
    @Deprecated
    void whereIn_array_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, 1L, 2L, 3L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIn_array_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, (Object) null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void whereIn_array_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIn(null, 1L, 2L, 3L)
        );
    }

    @Test
    @Deprecated
    void withFetch_singular_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void withFetch_singular_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .withFetch((SingularAttribute<GuitarPedal, Object>) null)
        );
    }

    @Test
    @Deprecated
    void withFetch_list_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.tags);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    @Deprecated
    void withFetch_list_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .withFetch((ListAttribute<GuitarPedal, Object>) null)
        );
    }

    @Test
    void isEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isEqualTo(GuitarPedal_.id, 0L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isEqualTo(GuitarPedal_.id, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isEqualTo(null, 0L)
        );
    }

    @Test
    void isNotEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isNotEqualTo(GuitarPedal_.id, 0L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isNotEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isNotEqualTo(GuitarPedal_.id, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isNotEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNotEqualTo(null, 0L)
        );
    }

    @Test
    void isLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isLike(null, "%test%")
        );
    }

    @Test
    void isNotLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isNotLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isNotLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isNotLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isNotLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNotLike(null, "%test%")
        );
    }

    @Test
    void isEqualToOrLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isEqualToOrLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isEqualsOrLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isEqualToOrLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isEqualToOrLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isEqualToOrLike(null, "%test%")
        );
    }

    @Test
    void isIsTrue_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isTrue(GuitarPedal_.hasStereoOutput);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIsTrue_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isTrue(null)
        );
    }

    @Test
    void isIsFalse_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isFalse(GuitarPedal_.hasStereoOutput);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIsFalse_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isFalse(null)
        );
    }

    @Test
    void isIsNull_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isNull(GuitarPedal_.dateSold);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIsNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNull(null)
        );
    }

    @Test
    void isIsNotNull_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isNotNull(GuitarPedal_.dateSold);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIsNotNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNotNull(null)
        );
    }

    @Test
    void isGreaterThan_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isGreaterThan(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isGreaterThan_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isGreaterThan(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isGreaterThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isGreaterThan(null, 0)
        );
    }

    @Test
    void isGreaterThanOrEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isGreaterThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isGreaterThanOrEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isGreaterThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isGreaterThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isGreaterThanOrEqualTo(null, 0)
        );
    }

    @Test
    void isLessThan_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isLessThan(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isLessThan_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isLessThan(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isLessThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isLessThan(null, 0)
        );
    }

    @Test
    void isLessThanOrEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isLessThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isLessThanOrEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isLessThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isLessThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isLessThanOrEqualTo(null, 0)
        );
    }

    @Test
    void isBetween_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isBetween(
                        GuitarPedal_.datePurchased,
                        LocalDate.now(),
                        LocalDate.now().plusDays(7)
                );
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isBetween_returnsBuilderWithSpecification_evenWhenEitherValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isBetween(GuitarPedal_.usedValue, null, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());

        builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isBetween(GuitarPedal_.usedValue, 0, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());

        builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isBetween(GuitarPedal_.usedValue, null, 9);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isBetween_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isBetween(null, 0L, 9L)
        );
    }

    @Test
    void isIn_collection_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isIn(GuitarPedal_.id, List.of(1L, 2L, 3L));
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIn_collection_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isIn(GuitarPedal_.id, (Collection<?>) null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIn_collection_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isIn(null, List.of(1L, 2L, 3L))
        );
    }

    @Test
    void isIn_array_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isIn(GuitarPedal_.id, 1L, 2L, 3L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIn_array_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .where().isIn(GuitarPedal_.id, (Object) null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void isIn_array_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .where().isIn(null, 1L, 2L, 3L)
        );
    }

    @Test
    void fetch_singular_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .with().fetchOf(GuitarPedal_.manufacturer);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void fetch_singular_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .with().fetchOf((SingularAttribute<GuitarPedal, Object>) null)
        );
    }

    @Test
    void fetch_list_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.from(GuitarPedal.class)
                .with().fetchOf(GuitarPedal_.tags);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void fetch_list_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.from(GuitarPedal.class)
                        .with().fetchOf((ListAttribute<GuitarPedal, Object>) null)
        );
    }
}