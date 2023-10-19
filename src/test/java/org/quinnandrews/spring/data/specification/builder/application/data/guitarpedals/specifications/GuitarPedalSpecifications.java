package org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.specifications;

import jakarta.persistence.criteria.Join;
import org.quinnandrews.spring.data.specification.builder.SpecificationBuilder;
import org.quinnandrews.spring.data.specification.builder.SpecificationFactory;
import org.quinnandrews.spring.data.specification.builder.SpecificationUtil;
import org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class GuitarPedalSpecifications {

    private final SpecificationFactory specificationFactory = new SpecificationFactory();

    public GuitarPedalSpecifications() {
        // no-op
    }

    public Specification<GuitarPedal> search_example_01(final Integer usedValueGreaterThan) {
        return Specification.where((Specification<GuitarPedal>) (root, query, builder) ->
                        builder.greaterThan(root.get(GuitarPedal_.usedValue), usedValueGreaterThan))
                .and((root, query, builder) ->
                        builder.isNull(root.get(GuitarPedal_.dateSold)));
    }

    public Specification<GuitarPedal> search_example_02(final Integer usedValueGreaterThan) {
        return Specification.where(hasNotBeenSold_verbose())
                .and(usedValueGreaterThan_verbose(usedValueGreaterThan));
    }

    public Specification<GuitarPedal> hasNotBeenSold_verbose() {
        return (root, query, builder) -> builder.isNull(root.get(GuitarPedal_.dateSold));
    }

    public Specification<GuitarPedal> usedValueGreaterThan_verbose(final Integer value) {
        return (root, query, builder) -> builder.greaterThan(root.get(GuitarPedal_.usedValue), value);
    }

    public Specification<GuitarPedal> search_example_03(final Integer usedValueGreaterThan) {
        final var specification = Specification.where(hasNotBeenSold_verbose());
        if (usedValueGreaterThan != null) {
            specification.and(usedValueGreaterThan_verbose(usedValueGreaterThan));
        }
        return specification;
    }

    public Specification<GuitarPedal> search_example_04(final Integer usedValueGreaterThan) {
        return  Specification.where(hasNotBeenSold())
                .and(usedValueGreaterThan(usedValueGreaterThan));
    }

    public Specification<GuitarPedal> hasNotBeenSold() {
        return specificationFactory.isNull(GuitarPedal_.dateSold);
    }

    public Specification<GuitarPedal> usedValueGreaterThan(final Integer value) {
        return specificationFactory.greaterThan(GuitarPedal_.usedValue, value);
    }

    public Specification<GuitarPedal> search_example_05(final Integer usedValueGreaterThan) {
        return  Specification.where(specificationFactory.isNull(GuitarPedal_.dateSold))
                .and(specificationFactory.greaterThan(GuitarPedal_.usedValue, usedValueGreaterThan));
    }

    public Specification<GuitarPedal> search_example_06(final Integer usedValueGreaterThan) {
        return specificationFactory.isNull(GuitarPedal_.dateSold)
                .and(specificationFactory.greaterThan(GuitarPedal_.usedValue, usedValueGreaterThan));
    }

    public Specification<GuitarPedal> search_example_07(final Integer usedValueGreaterThan) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereIsNull(GuitarPedal_.dateSold)
                .whereGreaterThan(GuitarPedal_.usedValue, usedValueGreaterThan)
                .toSpecification();
    }

    public Specification<GuitarPedal> fetch_example_01() {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer)
                .toSpecification();
    }

    public Specification<GuitarPedal> fetch_example_02(final String name) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer)
                .andWhere(
                        (root, query, builder) -> {
                            final var join = root.join(GuitarPedal_.manufacturer);
                            return builder.equal(join.get(Manufacturer_.name), name);
                        }
                )
                .toSpecification();
    }

    public Specification<GuitarPedal> fetch_example_03(final Collection<String> tags) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer)
                .withFetch(GuitarPedal_.tags)
                .andWhere(
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

    public Specification<GuitarPedal> fetch_example_04(final Collection<String> tags) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer)
                .withFetch(GuitarPedal_.tags)
                .andWhere(tagsContain(tags))
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

    public Specification<GuitarPedal> fetch_example_05(final Long id,
                                                       final Collection<String> tags) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer)
                .withFetch(GuitarPedal_.tags)
                .andWhere(
                        (root, query, builder) -> {
                            if (SpecificationUtil.noneAreNull(id)) {
                                final var join = root.join(GuitarPedal_.tags);
                                return builder.greaterThanOrEqualTo(join.get(GuitarPedalTag_.id), id);
                            }
                            return null;
                        }
                )
                .andWhere(
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

    public Specification<GuitarPedal> fetch_example_06(final Long id,
                                                       final Collection<String> tags) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer)
                .withFetch(GuitarPedal_.tags)
                .andWhere(
                        (root, query, builder) -> {
                            if (SpecificationUtil.noneAreNull(id, tags)) {
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

    @SuppressWarnings("unchecked")
    public Specification<GuitarPedal> fetch_example_07(final Long id,
                                                       final Collection<String> tags) {
        return SpecificationBuilder.withRoot(GuitarPedal.class)
                .withFetch(GuitarPedal_.manufacturer)
                .andWhere(
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

