package org.quinnandrews.spring.data.specification.builder;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.junit.jupiter.api.Test;
import org.quinnandrews.spring.data.specification.builder.application.Application;
import org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
class SpecificationBuilderTest {

    @Test
    void withRoot_returnsNewInstance() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class);
        assertNotNull(builder);
        assertNull(builder.toSpecification());
    }

    @Test
    void withRoot_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(null)
        );
    }

    @Test
    void toSpecification_returnsSpecification_ifAnyDefined() {
        assertNotNull(SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEquals(GuitarPedal_.id, 0L)
                .toSpecification());
    }

    @Test
    void toSpecification_returnsNull_whenNoSpecificationsDefined() {
        assertNull(SpecificationBuilder.withRoot(GuitarPedal.class)
                .toSpecification());
    }

    @Test
    void andWhere_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .andWhere(new SpecificationFactory().ghost());
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void andWhere_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .andWhere(null)
        );
    }

    @Test
    void orWhere_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .orWhere(new SpecificationFactory().ghost());
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void orWhere_throwsException_whenArgumentIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .orWhere(null)
        );
    }

    @Test
    void whereEquals_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEquals(GuitarPedal_.id, 0L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereEquals_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEquals(GuitarPedal_.id, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereEquals_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereEquals(null, 0L)
        );
    }

    @Test
    void whereNotEquals_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotEquals(GuitarPedal_.id, 0L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereNotEquals_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotEquals(GuitarPedal_.id, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereNotEquals_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereNotEquals(null, 0L)
        );
    }

    @Test
    void whereLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLike(null, "%test%")
        );
    }

    @Test
    void whereNotLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereNotLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereNotLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereNotLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereNotLike(null, "%test%")
        );
    }

    @Test
    void whereEqualsOrLike_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEqualsOrLike(GuitarPedal_.name, "%test%");
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereEqualsOrLike_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEqualsOrLike(GuitarPedal_.name, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereEqualsOrLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereEqualsOrLike(null, "%test%")
        );
    }

    @Test
    void whereIsTrue_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsTrue(GuitarPedal_.hasStereoOutput);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIsTrue_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsTrue(null)
        );
    }

    @Test
    void whereIsFalse_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsFalse(GuitarPedal_.hasStereoOutput);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIsFalse_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsFalse(null)
        );
    }

    @Test
    void whereIsNull_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsNull(GuitarPedal_.dateSold);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIsNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsNull(null)
        );
    }

    @Test
    void whereIsNotNull_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsNotNull(GuitarPedal_.dateSold);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIsNotNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsNotNull(null)
        );
    }

    @Test
    void whereGreaterThan_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThan(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereGreaterThan_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThan(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereGreaterThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereGreaterThan(null, 0)
        );
    }

    @Test
    void whereGreaterThanOrEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereGreaterThanOrEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereGreaterThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereGreaterThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereGreaterThanOrEqualTo(null, 0)
        );
    }

    @Test
    void whereLessThan_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThan(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereLessThan_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThan(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereLessThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLessThan(null, 0)
        );
    }

    @Test
    void whereLessThanOrEqualTo_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereLessThanOrEqualTo_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereLessThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereLessThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLessThanOrEqualTo(null, 0)
        );
    }

    @Test
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
    void whereBetween_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereBetween(null, 0L, 9L)
        );
    }

    @Test
    void whereIn_collection_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, List.of(1L, 2L, 3L));
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIn_collection_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, (Collection<?>) null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIn_collection_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIn(null, List.of(1L, 2L, 3L))
        );
    }

    @Test
    void whereIn_array_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, 1L, 2L, 3L);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIn_array_returnsBuilderWithSpecification_evenWhenValueIsNull() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIn(GuitarPedal_.id, (Object) null);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void whereIn_array_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIn(null, 1L, 2L, 3L)
        );
    }

    @Test
    void withFetch_singular_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void withFetch_singular_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .withFetch((SingularAttribute<GuitarPedal, Object>) null)
        );
    }

    @Test
    void withFetch_list_returnsBuilderWithSpecification() {
        var builder = SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.tags);
        assertNotNull(builder);
        assertNotNull(builder.toSpecification());
    }

    @Test
    void withFetch_list_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationBuilder.withRoot(GuitarPedal.class)
                        .withFetch((ListAttribute<GuitarPedal, Object>) null)
        );
    }
}