package io.github.quinnandrews.spring.data.specification.builder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.github.quinnandrews.spring.data.specification.builder.application.Application;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static io.github.quinnandrews.spring.data.specification.builder.SpecificationFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
class SpecificationFactoryTest {

    @Autowired
    private EntityManager entityManager;

    private CriteriaBuilder builder;
    private CriteriaQuery<GuitarPedal> query;
    private Root<GuitarPedal> root;

    @BeforeEach
    void setUp() {
        builder = entityManager.getCriteriaBuilder();
        query = builder.createQuery(GuitarPedal.class);
        root = query.from(GuitarPedal.class);
    }

    @Test
    void isEqualTo_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isEqualTo(GuitarPedal_.id, 0L);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isEqualTo_returnsNullPredicate_whenValueIsNull() {
        var specification = isEqualTo(GuitarPedal_.id, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isEqualTo(null, 0L));
    }

    @Test
    void isNotEqual_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isNotEqualTo(GuitarPedal_.id, 0L);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNotEqual_returnsNullPredicate_whenValueIsNull() {
        var specification = isNotEqualTo(GuitarPedal_.id, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNotEqual_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isNotEqualTo(null, 0L));
    }

    @Test
    void isLike_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isLike(GuitarPedal_.name, "%test%");
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isLike_returnsNullPredicate_whenValueIsNull() {
        var specification = isLike(GuitarPedal_.name, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isLike(null, "%test%"));
    }

    @Test
    void isNotLike_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isNotLike(GuitarPedal_.name, "%test%");
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNotLike_returnsNullPredicate_whenValueIsNull() {
        var specification = isNotLike(GuitarPedal_.name, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNotLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isNotLike(null, "%test%"));
    }

    @Test
    void isEqualToOrLike_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isEqualToOrLike(GuitarPedal_.name, "%test%");
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isEqualToOrLike_returnsNullPredicate_whenValueIsNull() {
        var specification = isEqualToOrLike(GuitarPedal_.name, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isEqualToOrLike_callsIsEqualTo_whenValueContainsNoWildcards() {
        try (var mockedStatic = mockStatic(
                SpecificationFactory.class,
                Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS))) {
            isEqualToOrLike(GuitarPedal_.name, "test");
            mockedStatic.verify(() -> SpecificationFactory.isEqualTo(GuitarPedal_.name,"test"), times(1));
            mockedStatic.verify(() -> SpecificationFactory.isLike(GuitarPedal_.name,"test"), times(0));
        }
    }

    @Test
    void isEqualToOrLike_callsIsLike_whenValueContainsWildcards() {
        try (var mockedStatic = mockStatic(
                SpecificationFactory.class,
                Mockito.withSettings().defaultAnswer(Mockito.CALLS_REAL_METHODS))) {
            isEqualToOrLike(GuitarPedal_.name, "%test%");
            mockedStatic.verify(() -> SpecificationFactory.isEqualTo(GuitarPedal_.name,"%test%"), times(0));
            mockedStatic.verify(() -> SpecificationFactory.isLike(GuitarPedal_.name,"%test%"), times(1));
        }
    }

    @Test
    void isEqualToOrLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isEqualToOrLike(null, "%test%"));
    }

    @Test
    void isTrue_returnsNonNullPredicate() {
        var specification = isTrue(GuitarPedal_.hasStereoOutput);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isTrue_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isTrue(null));
    }

    @Test
    void isFalse_returnsNonNullPredicate() {
        var specification = isFalse(GuitarPedal_.hasStereoOutput);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isFalse_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isFalse(null));
    }

    @Test
    void isNull_returnsNonNullPredicate() {
        var specification = isNull(GuitarPedal_.dateSold);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationFactory.isNull(null));
    }

    @Test
    void isNotNull_returnsNonNullPredicate() {
        var specification = isNotNull(GuitarPedal_.dateSold);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNotNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> SpecificationFactory.isNotNull(null));
    }

    @Test
    void isGreaterThan_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isGreaterThan(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isGreaterThan_returnsNullPredicate_whenValueIsNull() {
        var specification = isGreaterThan(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isGreaterThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isGreaterThan(null, 0));
    }

    @Test
    void isGreaterThanOrEqualTo_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isGreaterThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isGreaterThanOrEqualTo_returnsNullPredicate_whenValueIsNull() {
        var specification = isGreaterThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isGreaterThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isGreaterThanOrEqualTo(null, 0));
    }

    @Test
    void isLessThan_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isLessThan(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isLessThan_returnsNullPredicate_whenValueIsNull() {
        var specification = isLessThan(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isLessThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isLessThan(null, 0));
    }

    @Test
    void isLessThanOrEqualTo_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isLessThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isLessThanOrEqualTo_returnsNullPredicate_whenValueIsNull() {
        var specification = isLessThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isLessThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isLessThanOrEqualTo(null, 0));
    }

    @Test
    void isBetween_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isBetween(
                GuitarPedal_.datePurchased,
                LocalDate.now(), 
                LocalDate.now().plusDays(7)
        );
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isBetween_returnsNullPredicate_whenEitherValueIsNull() {
        var specification = isBetween(GuitarPedal_.usedValue, null, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));

        specification = isBetween(GuitarPedal_.usedValue, 0, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));

        specification = isBetween(GuitarPedal_.usedValue, null, 9);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isBetween_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isBetween(null, 0L, 9L));
    }

    @Test
    void isIn_collection_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isIn(GuitarPedal_.id, List.of(1L, 2L, 3L));
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isIn_collection_returnsNullPredicate_whenValueIsNull() {
        var specification = isIn(GuitarPedal_.id, (Collection<?>) null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isIn_collection_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isIn(null, List.of(1L, 2L, 3L)));
    }

    @Test
    void isIn_array_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = isIn(GuitarPedal_.id, 1L, 2L, 3L);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isIn_array_returnsNullPredicate_whenValueIsNull() {
        var specification = isIn(GuitarPedal_.id, (Object) null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isIn_array_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> isIn(null, 1L, 2L, 3L));
    }

    @Test
    void fetch_singular_returnsNonNullPredicate() {
        var specification = fetch(GuitarPedal_.manufacturer);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void fetch_singular_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> fetch((SingularAttribute<GuitarPedal, Object>) null));
    }

    @Test
    void fetch_list_returnsNonNullPredicate() {
        var specification = fetch(GuitarPedal_.tags);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void fetch_list_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> fetch((ListAttribute<GuitarPedal, Object>) null));
    }

    @Test
    void ghost_returnsNullPredicate() {
        Specification<GuitarPedal> specification = ghost();
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }
}