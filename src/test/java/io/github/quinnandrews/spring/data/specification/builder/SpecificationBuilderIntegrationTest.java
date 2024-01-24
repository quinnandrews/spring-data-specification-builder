package io.github.quinnandrews.spring.data.specification.builder;

import org.junit.jupiter.api.Test;
import io.github.quinnandrews.spring.data.specification.builder.application.Application;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedal_;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.repository.GuitarPedalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
public class SpecificationBuilderIntegrationTest {

    @Autowired
    private GuitarPedalRepository guitarPedalRepository;

    @Test
    @Deprecated
    void whereEquals() {
        var optionalPedal = guitarPedalRepository.findOne(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereEquals(GuitarPedal_.id, 3L)
                        .toSpecification());
        assertTrue(optionalPedal.isPresent());
        assertEquals(3L, optionalPedal.get().getId());
        assertEquals("Soft Focus Reverb", optionalPedal.get().getName());
    }

    @Test
    @Deprecated
    void whereNotEquals() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereNotEquals(GuitarPedal_.id, 3L)
                        .toSpecification());
        assertEquals(3, pedals.size());
        assertTrue(pedals.stream()
                .noneMatch(p -> p.getId().equals(3L)));
        assertTrue(pedals.stream()
                .noneMatch(p -> p.getName().equals("Soft Focus Reverb")));
    }

    @Test
    @Deprecated
    void whereLike() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLike(GuitarPedal_.name, "%and%")
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(1).getName());
    }

    @Test
    @Deprecated
    void whereNotLike() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereNotLike(GuitarPedal_.name, "%and%")
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }

    @Test
    @Deprecated
    void whereEqualsOrLike() {
        var pedals = guitarPedalRepository.findAll(SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEqualsOrLike(GuitarPedal_.name, "Deco%")
                .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
    }

    @Test
    @Deprecated
    void whereIsNull() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsNull(GuitarPedal_.dateSold)
                        .toSpecification(), Sort.by("name"));
        assertEquals(3, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertNull(pedals.get(0).getDateSold());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertNull(pedals.get(1).getDateSold());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertNull(pedals.get(2).getDateSold());
    }

    @Test
    @Deprecated
    void whereIsNotNull() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsNotNull(GuitarPedal_.dateSold)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(0).getName());
        assertNotNull(pedals.get(0).getDateSold());
    }

    @Test
    @Deprecated
    void whereIsTrue() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsTrue(GuitarPedal_.hasStereoOutput)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertTrue(pedals.get(0).getHasStereoOutput());

    }

    @Test
    @Deprecated
    void whereIsFalse() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIsFalse(GuitarPedal_.hasStereoOutput)
                        .toSpecification(), Sort.by("name"));
        assertEquals(3, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertFalse(pedals.get(0).getHasStereoOutput());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(1).getName());
        assertFalse(pedals.get(1).getHasStereoOutput());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertFalse(pedals.get(2).getHasStereoOutput());
    }

    @Test
    @Deprecated
    void whereGreaterThan() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereGreaterThan(GuitarPedal_.usedValue, 200)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
    }

    @Test
    @Deprecated
    void whereGreaterThanOrEqualTo() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereGreaterThanOrEqualTo(GuitarPedal_.usedValue, 200)
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
    }

    @Test
    @Deprecated
    void whereLessThan() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLessThan(GuitarPedal_.usedValue, 150)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
    }

    @Test
    @Deprecated
    void whereLessThanOrEqualTo() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLessThanOrEqualTo(GuitarPedal_.usedValue, 150)
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(1).getName());
        assertEquals(150, pedals.get(1).getUsedValue());
    }

    @Test
    @Deprecated
    void whereBetween() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereBetween(
                                GuitarPedal_.datePurchased,
                                LocalDate.of(2021, 7, 19),
                                LocalDate.of(2022, 9, 11))
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(LocalDate.of(2021, 7, 19), pedals.get(0).getDatePurchased());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(LocalDate.of(2022, 9, 11), pedals.get(1).getDatePurchased());
    }

    @Test
    @Deprecated
    void whereIn_collection() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIn(GuitarPedal_.id, List.of(2L, 3L))
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }

    @Test
    @Deprecated
    void whereIn_array() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereIn(GuitarPedal_.id, 2L, 3L)
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }

    @Test
    @Deprecated
    void orWhere() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereEquals(GuitarPedal_.id, 2L)
                        .orWhere(SpecificationBuilder.withRoot(GuitarPedal.class)
                                .whereEquals(GuitarPedal_.id, 3L)
                                .toSpecification())
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }

    @Test
    @Deprecated
    void andWhere() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.withRoot(GuitarPedal.class)
                        .whereLike(GuitarPedal_.name, "%and%")
                        .andWhere(SpecificationBuilder.withRoot(GuitarPedal.class)
                                .whereIsNull(GuitarPedal_.dateSold)
                                .toSpecification())
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertNull(pedals.get(0).getDateSold());
    }

    @Test
    void where_withSpecification() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where(SpecificationBuilder.from(GuitarPedal.class)
                                .where().isNull(GuitarPedal_.dateSold)
                                .toSpecification())
                        .and().isLike(GuitarPedal_.name, "%and%")
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertNull(pedals.get(0).getDateSold());
    }

    @Test
    void and_withSpecification() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isLike(GuitarPedal_.name, "%and%")
                        .and(SpecificationBuilder.from(GuitarPedal.class)
                                .where().isNull(GuitarPedal_.dateSold)
                                .toSpecification())
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertNull(pedals.get(0).getDateSold());
    }

    @Test
    void or_withSpecification() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isEqualTo(GuitarPedal_.id, 2L)
                        .or(SpecificationBuilder.from(GuitarPedal.class)
                                .where().isEqualTo(GuitarPedal_.id, 3L)
                                .toSpecification())
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }

    @Test
    void isEqualTo() {
        var optionalPedal = guitarPedalRepository.findOne(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isEqualTo(GuitarPedal_.id, 3L)
                        .toSpecification());
        assertTrue(optionalPedal.isPresent());
        assertEquals(3L, optionalPedal.get().getId());
        assertEquals("Soft Focus Reverb", optionalPedal.get().getName());
    }

    @Test
    void isNotEqualTo() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNotEqualTo(GuitarPedal_.id, 3L)
                        .toSpecification());
        assertEquals(3, pedals.size());
        assertTrue(pedals.stream()
                .noneMatch(p -> p.getId().equals(3L)));
        assertTrue(pedals.stream()
                .noneMatch(p -> p.getName().equals("Soft Focus Reverb")));
    }

    @Test
    void isLike() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isLike(GuitarPedal_.name, "%and%")
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(1).getName());
    }

    @Test
    void isNotLike() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNotLike(GuitarPedal_.name, "%and%")
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }

    @Test
    void isEqualToOrLike() {
        var pedals = guitarPedalRepository.findAll(SpecificationBuilder.from(GuitarPedal.class)
                .where().isEqualToOrLike(GuitarPedal_.name, "Deco%")
                .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
    }

    @Test
    void isNull() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNull(GuitarPedal_.dateSold)
                        .toSpecification(), Sort.by("name"));
        assertEquals(3, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertNull(pedals.get(0).getDateSold());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertNull(pedals.get(1).getDateSold());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertNull(pedals.get(2).getDateSold());
    }

    @Test
    void isNotNull() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isNotNull(GuitarPedal_.dateSold)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(0).getName());
        assertNotNull(pedals.get(0).getDateSold());
    }

    @Test
    void isTrue() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isTrue(GuitarPedal_.hasStereoOutput)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertTrue(pedals.get(0).getHasStereoOutput());

    }

    @Test
    void isFalse() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isFalse(GuitarPedal_.hasStereoOutput)
                        .toSpecification(), Sort.by("name"));
        assertEquals(3, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertFalse(pedals.get(0).getHasStereoOutput());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(1).getName());
        assertFalse(pedals.get(1).getHasStereoOutput());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertFalse(pedals.get(2).getHasStereoOutput());
    }

    @Test
    void isGreaterThan() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isGreaterThan(GuitarPedal_.usedValue, 200)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
    }

    @Test
    void isGreaterThanOrEqualTo() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isGreaterThanOrEqualTo(GuitarPedal_.usedValue, 200)
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
    }

    @Test
    void isLessThan() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isLessThan(GuitarPedal_.usedValue, 150)
                        .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
    }

    @Test
    void isLessThanOrEqualTo() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isLessThanOrEqualTo(GuitarPedal_.usedValue, 150)
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(1).getName());
        assertEquals(150, pedals.get(1).getUsedValue());
    }

    @Test
    void isBetween() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isBetween(
                                GuitarPedal_.datePurchased,
                                LocalDate.of(2021, 7, 19),
                                LocalDate.of(2022, 9, 11))
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(LocalDate.of(2021, 7, 19), pedals.get(0).getDatePurchased());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(LocalDate.of(2022, 9, 11), pedals.get(1).getDatePurchased());
    }

    @Test
    void isIn_collection() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isIn(GuitarPedal_.id, List.of(2L, 3L))
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }

    @Test
    void isIn_array() {
        var pedals = guitarPedalRepository.findAll(
                SpecificationBuilder.from(GuitarPedal.class)
                        .where().isIn(GuitarPedal_.id, 2L, 3L)
                        .toSpecification(), Sort.by("name"));
        assertEquals(2, pedals.size());
        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
    }
}
