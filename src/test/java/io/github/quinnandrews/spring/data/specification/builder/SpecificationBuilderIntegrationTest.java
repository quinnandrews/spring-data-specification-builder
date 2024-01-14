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
    void whereEqualsOrLike() {
        var pedals = guitarPedalRepository.findAll(SpecificationBuilder.withRoot(GuitarPedal.class)
                .whereEqualsOrLike(GuitarPedal_.name, "Deco%")
                .toSpecification(), Sort.by("name"));
        assertEquals(1, pedals.size());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
    }

    @Test
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
}
