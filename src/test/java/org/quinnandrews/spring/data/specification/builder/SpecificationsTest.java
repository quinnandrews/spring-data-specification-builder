package org.quinnandrews.spring.data.specification.builder;

import org.junit.jupiter.api.Test;
import org.quinnandrews.spring.data.specification.builder.application.Application;
import org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.repository.GuitarPedalRepository;
import org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.specifications.GuitarPedalSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = Application.class)
public class SpecificationsTest {

    @Autowired
    private GuitarPedalSpecifications guitarPedalSpecifications;

    @Autowired
    private GuitarPedalRepository guitarPedalRepository;

    @Test
    void search_example_01() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_01(null)
        );
        assertTrue(pedals.isEmpty());
    }

    @Test
    void fetch_example_01() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_01(),
                Sort.by("name")
        );
        assertEquals(4, pedals.size());
    }

    @Test
    void fetch_example_02() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_02("Strymon"),
                Sort.by("name")
        );
        assertEquals(1, pedals.size());
    }

    @Test
    void fetch_example_03() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_03(List.of("shoegaze")),
                Sort.by("name")
        );
        pedals.forEach(p -> System.out.println(p.getName()));
        pedals.forEach(p -> p.getTags().forEach(t -> System.out.println(t.getTag())));
        assertEquals(2, pedals.size());
    }

    @Test
    void fetch_example_04() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_04(List.of("shoegaze")),
                Sort.by("name")
        );
        pedals.forEach(p -> System.out.println(p.getName()));
        pedals.forEach(p -> p.getTags().forEach(t -> System.out.println(t.getTag())));
        assertEquals(2, pedals.size());
    }

    @Test
    void fetch_example_05() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_05(300L, List.of("shoegaze")),
                Sort.by("name")
        );
        pedals.forEach(p -> System.out.println(p.getName()));
        pedals.forEach(p -> p.getTags().forEach(t -> System.out.println(t.getTag())));
        assertEquals(1, pedals.size());
        assertEquals(16, pedals.get(0).getTags().size());
    }

    @Test
    void fetch_example_06() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_06(300L, List.of("shoegaze")),
                Sort.by("name")
        );
        pedals.forEach(p -> System.out.println(p.getName()));
        pedals.forEach(p -> p.getTags().forEach(t -> System.out.println(t.getTag())));
        assertEquals(1, pedals.size());
        assertEquals(4, pedals.get(0).getTags().size());
    }

    @Test
    void fetch_example_07() {
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_07(300L, List.of("shoegaze")),
                Sort.by("name")
        );
        pedals.forEach(p -> System.out.println(p.getName()));
        pedals.forEach(p -> p.getTags().forEach(t -> System.out.println(t.getTag())));
        assertEquals(1, pedals.size());
        assertEquals(1, pedals.get(0).getTags().size());
    }
}
