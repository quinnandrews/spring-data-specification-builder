package org.quinnandrews.spring.data.specification.builder;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.quinnandrews.spring.data.specification.builder.application.Application;
import org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = Application.class)
class SpecificationFactoryTest {

    private final SpecificationFactory specificationFactory = new SpecificationFactory();

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
    void equal_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.equal(GuitarPedal_.id, 0L);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void equal_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.equal(GuitarPedal_.id, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void equal_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.equal(null, 0L));
    }

    @Test
    void notEqual_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.notEqual(GuitarPedal_.id, 0L);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void notEqual_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.notEqual(GuitarPedal_.id, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void notEqual_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.notEqual(null, 0L));
    }

    @Test
    void like_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.like(GuitarPedal_.name, "%test%");
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void like_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.like(GuitarPedal_.name, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void like_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.like(null, "%test%"));
    }

    @Test
    void notLike_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.notLike(GuitarPedal_.name, "%test%");
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void notLike_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.notLike(GuitarPedal_.name, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void notLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.notLike(null, "%test%"));
    }

    @Test
    void equalOrLike_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.equalOrLike(GuitarPedal_.name, "%test%");
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void equalOrLike_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.equalOrLike(GuitarPedal_.name, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void equalOrLike_callsEqual_whenValueContainsNoWildcards() {
        var specificationFactory = spy(this.specificationFactory);
        specificationFactory.equalOrLike(GuitarPedal_.name, "test");
        verify(specificationFactory, times(1)).equal(GuitarPedal_.name, "test");
        verify(specificationFactory, times(0)).like(GuitarPedal_.name, "test");
    }

    @Test
    void equalOrLike_callsLike_whenValueContainsWildcards() {
        var specificationFactory = spy(this.specificationFactory);
        specificationFactory.equalOrLike(GuitarPedal_.name, "%test%");
        verify(specificationFactory, times(0)).equal(GuitarPedal_.name, "%test%");
        verify(specificationFactory, times(1)).like(GuitarPedal_.name, "%test%");
    }

    @Test
    void equalOrLike_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.equalOrLike(null, "%test%"));
    }

    @Test
    void isTrue_returnsNonNullPredicate() {
        var specification = specificationFactory.isTrue(GuitarPedal_.hasStereoOutput);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isTrue_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.isTrue(null));
    }

    @Test
    void isFalse_returnsNonNullPredicate() {
        var specification = specificationFactory.isFalse(GuitarPedal_.hasStereoOutput);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isFalse_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.isFalse(null));
    }

    @Test
    void isNull_returnsNonNullPredicate() {
        var specification = specificationFactory.isNull(GuitarPedal_.dateSold);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.isNull(null));
    }

    @Test
    void isNotNull_returnsNonNullPredicate() {
        var specification = specificationFactory.isNotNull(GuitarPedal_.dateSold);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void isNotNull_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.isNotNull(null));
    }

    @Test
    void greaterThan_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.greaterThan(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void greaterThan_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.greaterThan(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void greaterThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.greaterThan(null, 0));
    }

    @Test
    void greaterThanOrEqualTo_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.greaterThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void greaterThanOrEqualTo_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.greaterThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void greaterThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.greaterThanOrEqualTo(null, 0));
    }

    @Test
    void lessThan_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.lessThan(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void lessThan_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.lessThan(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void lessThan_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.lessThan(null, 0));
    }

    @Test
    void lessThanOrEqualTo_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.lessThanOrEqualTo(GuitarPedal_.usedValue, 0);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void lessThanOrEqualTo_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.lessThanOrEqualTo(GuitarPedal_.usedValue, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void lessThanOrEqualTo_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.lessThanOrEqualTo(null, 0));
    }

    @Test
    void between_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.between(
                GuitarPedal_.datePurchased,
                LocalDate.now(), 
                LocalDate.now().plusDays(7)
        );
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void between_returnsNullPredicate_whenEitherValueIsNull() {
        var specification = specificationFactory.between(GuitarPedal_.usedValue, null, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));

        specification = specificationFactory.between(GuitarPedal_.usedValue, 0, null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));

        specification = specificationFactory.between(GuitarPedal_.usedValue, null, 9);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void between_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.between(null, 0L, 9L));
    }

    @Test
    void in_collection_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.in(GuitarPedal_.id, List.of(1L, 2L, 3L));
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void in_collection_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.in(GuitarPedal_.id, (Collection<?>) null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void in_collection_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.in(null, List.of(1L, 2L, 3L)));
    }

    @Test
    void in_array_returnsNonNullPredicate_whenValueIsNotNull() {
        var specification = specificationFactory.in(GuitarPedal_.id, 1L, 2L, 3L);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void in_array_returnsNullPredicate_whenValueIsNull() {
        var specification = specificationFactory.in(GuitarPedal_.id, (Object) null);
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void in_array_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.in(null, 1L, 2L, 3L));
    }

    @Test
    void fetch_singular_returnsNonNullPredicate() {
        var specification = specificationFactory.fetch(GuitarPedal_.manufacturer);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void fetch_singular_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.fetch((SingularAttribute<GuitarPedal, Object>) null));
    }

    @Test
    void fetch_list_returnsNonNullPredicate() {
        var specification = specificationFactory.fetch(GuitarPedal_.tags);
        assertNotNull(specification);
        assertNotNull(specification.toPredicate(root, query, builder));
    }

    @Test
    void fetch_list_throwsException_whenAttributeIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> specificationFactory.fetch((ListAttribute<GuitarPedal, Object>) null));
    }

    @Test
    void ghost_returnsNullPredicate() {
        Specification<GuitarPedal> specification = specificationFactory.ghost();
        assertNotNull(specification);
        assertNull(specification.toPredicate(root, query, builder));
    }
}