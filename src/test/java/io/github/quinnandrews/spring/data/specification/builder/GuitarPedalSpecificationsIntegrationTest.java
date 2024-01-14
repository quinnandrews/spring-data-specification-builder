package io.github.quinnandrews.spring.data.specification.builder;

import io.github.quinnandrews.spring.data.specification.builder.application.Application;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.GuitarPedalTag;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.repository.GuitarPedalRepository;
import org.junit.jupiter.api.Test;
import io.github.quinnandrews.spring.data.specification.builder.application.data.guitarpedals.specifications.GuitarPedalSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Application.class)
public class GuitarPedalSpecificationsIntegrationTest {

    @Autowired
    private GuitarPedalSpecifications guitarPedalSpecifications;

    @Autowired
    private GuitarPedalRepository guitarPedalRepository;

    @Test
    void search_example_01() {
        // when passing in a value of 75
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_01(75),
                Sort.by("name")
        );
        // then the value is included from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 2 pedals are returned
        // and both have a used value greater than 75
        // and both hava a null date sold
        assertEquals(2, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());
    }

    @Test
    void search_example_01_doesNotIgnoreNullValues() {
        // when passing in a null value
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_01(null)
        );
        // then the value is still included in the query building process
        // and NO pedals are returned
        assertTrue(pedals.isEmpty());
    }

    @Test
    void search_example_02() {
        // when passing in a value of 75
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_02(75),
                Sort.by("name")
        );
        // then the value is included from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 2 pedals are returned
        // and both have a used value greater than 75
        // and both hava a null date sold
        assertEquals(2, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());
    }

    @Test
    void search_example_02_doesNotIgnoreNullValues() {
        // when passing in a null value
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_02(null)
        );
        // then the value is still included in the query building process
        // and NO pedals are returned
        assertTrue(pedals.isEmpty());
    }

    @Test
    void search_example_03() {
        // when passing in a value of 75
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_03(75),
                Sort.by("name")
        );
        // then the value is included from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 2 pedals are returned
        // and both have a used value greater than 75
        // and both hava a null date sold
        assertEquals(2, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());
    }

    @Test
    void search_example_03_ignoresNullValues() {
        // when passing in a null value
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_03(null),
                Sort.by("name")
        );
        // then the value is excluded from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 3 pedals are returned
        // and all hava a null date sold
        assertEquals(3, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(2L, pedals.get(1).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertEquals(250, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());

        assertEquals(3L, pedals.get(2).getId());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertEquals(200, pedals.get(2).getUsedValue());
        assertNull(pedals.get(2).getDateSold());
    }

    @Test
    void search_example_04() {
        // when passing in a value of 75
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_04(75),
                Sort.by("name")
        );
        // then the value is included from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 2 pedals are returned
        // and both have a used value greater than 75
        // and both hava a null date sold
        assertEquals(2, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());
    }

    @Test
    void search_example_04_ignoresNullValues() {
        // when passing in a null value
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_04(null),
                Sort.by("name")
        );
        // then the value is excluded from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 3 pedals are returned
        // and all hava a null date sold
        assertEquals(3, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(2L, pedals.get(1).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertEquals(250, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());

        assertEquals(3L, pedals.get(2).getId());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertEquals(200, pedals.get(2).getUsedValue());
        assertNull(pedals.get(2).getDateSold());
    }

    @Test
    void search_example_05() {
        // when passing in a value of 75
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_05(75),
                Sort.by("name")
        );
        // then the value is included from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 2 pedals are returned
        // and both have a used value greater than 75
        // and both hava a null date sold
        assertEquals(2, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());
    }

    @Test
    void search_example_05_ignoresNullValues() {
        // when passing in a null value
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_05(null),
                Sort.by("name")
        );
        // then the value is excluded from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 3 pedals are returned
        // and all hava a null date sold
        assertEquals(3, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(2L, pedals.get(1).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertEquals(250, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());

        assertEquals(3L, pedals.get(2).getId());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertEquals(200, pedals.get(2).getUsedValue());
        assertNull(pedals.get(2).getDateSold());
    }

    @Test
    void search_example_06() {
        // when passing in a value of 75
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_06(75),
                Sort.by("name")
        );
        // then the value is included from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 2 pedals are returned
        // and both have a used value greater than 75
        // and both hava a null date sold
        assertEquals(2, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());
    }

    @Test
    void search_example_06_ignoresNullValues() {
        // when passing in a null value
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_06(null),
                Sort.by("name")
        );
        // then the value is excluded from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 3 pedals are returned
        // and all hava a null date sold
        assertEquals(3, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(2L, pedals.get(1).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertEquals(250, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());

        assertEquals(3L, pedals.get(2).getId());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertEquals(200, pedals.get(2).getUsedValue());
        assertNull(pedals.get(2).getDateSold());
    }

    @Test
    void search_example_07() {
        // when passing in a value of 75
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_07(75),
                Sort.by("name")
        );
        // then the value is included in the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 2 pedals are returned
        // and both have a used value greater than 75
        // and both hava a null date sold
        assertEquals(2, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals(250, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals(200, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());
    }

    @Test
    void search_example_07_ignoresNullValues() {
        // when passing in a null value
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.search_example_07(null),
                Sort.by("name")
        );
        // then the value is excluded from the query building process
        // but the query has a built-in filter to exclude pedals that have been sold
        // and 3 pedals are returned
        // and all hava a null date sold
        assertEquals(3, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals(75, pedals.get(0).getUsedValue());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(2L, pedals.get(1).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertEquals(250, pedals.get(1).getUsedValue());
        assertNull(pedals.get(1).getDateSold());

        assertEquals(3L, pedals.get(2).getId());
        assertEquals("Soft Focus Reverb", pedals.get(2).getName());
        assertEquals(200, pedals.get(2).getUsedValue());
        assertNull(pedals.get(2).getDateSold());
    }

    @Test
    void fetch_example_01() {
        // when executing a query that defines a fetch
        // and has no filters
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_01(),
                Sort.by("name")
        );
        // then all four pedals are returned
        assertEquals(4, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals("Electro-Harmonix", pedals.get(0).getManufacturer().getName());
        assertNull(pedals.get(0).getDateSold());

        assertEquals(2L, pedals.get(1).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(1).getName());
        assertEquals("Strymon", pedals.get(1).getManufacturer().getName());
        assertNull(pedals.get(1).getDateSold());

        assertEquals(4L, pedals.get(2).getId());
        assertEquals("Sneak Attack: Attack/Decay and Tremolo", pedals.get(2).getName());
        assertEquals("Malekko", pedals.get(2).getManufacturer().getName());
        assertNotNull(pedals.get(2).getDateSold());

        assertEquals(3L, pedals.get(3).getId());
        assertEquals("Soft Focus Reverb", pedals.get(3).getName());
        assertEquals("Catalinbread", pedals.get(3).getManufacturer().getName());
        assertNull(pedals.get(3).getDateSold());
    }

    @Test
    void fetch_example_02() {
        // when pedals are filtered by the name of an associated manufacturer
        // with a value of 'Strymon'
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_02("Strymon"),
                Sort.by("name")
        );
        // then 1 pedal is returned
        // and the pedal has a manufacturer named 'Strymon'
        assertEquals(1, pedals.size());

        assertEquals(2L, pedals.get(0).getId());
        assertEquals("Deco: Tape Saturation and Double Tracker", pedals.get(0).getName());
        assertEquals("Strymon", pedals.get(0).getManufacturer().getName());
    }

    @Test
    void fetch_example_03() {
        // when pedals are filtered by the tag of an associated collection of guitarPedalTags
        // with a value of 'shoegaze'
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_03(List.of("shoegaze")),
                Sort.by("name")
        );
        // then 2 pedals are returned
        // and the first pedal has two tags
        // and one of them is 'shoegaze'
        // and the second pedal has four tags
        // and one of them is 'shoegaze'
        assertEquals(2, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals("Electro-Harmonix", pedals.get(0).getManufacturer().getName());
        assertEquals(2, pedals.get(0).getTags().size());
        pedals.get(0).getTags().sort(Comparator.comparing(GuitarPedalTag::getTag));
        assertEquals("fuzz", pedals.get(0).getTags().get(0).getTag());
        assertEquals("shoegaze", pedals.get(0).getTags().get(1).getTag());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals("Catalinbread", pedals.get(1).getManufacturer().getName());
        assertEquals(4, pedals.get(1).getTags().size());
        pedals.get(1).getTags().sort(Comparator.comparing(GuitarPedalTag::getTag));
        assertEquals("90s", pedals.get(1).getTags().get(0).getTag());
        assertEquals("lo-fi", pedals.get(1).getTags().get(1).getTag());
        assertEquals("reverb", pedals.get(1).getTags().get(2).getTag());
        assertEquals("shoegaze", pedals.get(1).getTags().get(3).getTag());
    }

    @Test
    void fetch_example_04() {
        // when pedals are filtered by the tag of an associated collection of guitarPedalTags
        // with a value of 'shoegaze'
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_04(List.of("shoegaze")),
                Sort.by("name")
        );
        // then 2 pedals are returned
        // and the first pedal has two tags
        // and one of them is 'shoegaze'
        // and the second pedal has four tags
        // and one of them is 'shoegaze'
        assertEquals(2, pedals.size());

        assertEquals(1L, pedals.get(0).getId());
        assertEquals("Big Muff Fuzz", pedals.get(0).getName());
        assertEquals("Electro-Harmonix", pedals.get(0).getManufacturer().getName());
        assertEquals(2, pedals.get(0).getTags().size());
        pedals.get(0).getTags().sort(Comparator.comparing(GuitarPedalTag::getTag));
        assertEquals("fuzz", pedals.get(0).getTags().get(0).getTag());
        assertEquals("shoegaze", pedals.get(0).getTags().get(1).getTag());

        assertEquals(3L, pedals.get(1).getId());
        assertEquals("Soft Focus Reverb", pedals.get(1).getName());
        assertEquals("Catalinbread", pedals.get(1).getManufacturer().getName());
        assertEquals(4, pedals.get(1).getTags().size());
        pedals.get(1).getTags().sort(Comparator.comparing(GuitarPedalTag::getTag));
        assertEquals("90s", pedals.get(1).getTags().get(0).getTag());
        assertEquals("lo-fi", pedals.get(1).getTags().get(1).getTag());
        assertEquals("reverb", pedals.get(1).getTags().get(2).getTag());
        assertEquals("shoegaze", pedals.get(1).getTags().get(3).getTag());
    }

    @Test
    void fetch_example_05() {
        // when pedals are filtered by two properties of an associated collection of guitarPedalTags
        // where the tag equals 'shoegaze'
        // and where the id is greater than or equal to 300
        // but the query is malformed with too many joins
        // and one join is for the fetch
        // and there is one join for each filter
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_05(300L, List.of("shoegaze"))
        );
        // then 1 pedal is returned
        // and the pedal has a tag named 'shoegaze'
        // and the pedal has tags with ids greater than or equal to 300
        // but the pedal is returned with 16 tags instead of the expected 4
        assertEquals(1, pedals.size());

        assertEquals(3L, pedals.get(0).getId());
        assertEquals("Soft Focus Reverb", pedals.get(0).getName());
        assertEquals("Catalinbread", pedals.get(0).getManufacturer().getName());

        assertEquals(16, pedals.get(0).getTags().size());
        pedals.get(0).getTags().sort(Comparator.comparing(GuitarPedalTag::getTag));
        assertEquals("90s", pedals.get(0).getTags().get(0).getTag());
        assertEquals("90s", pedals.get(0).getTags().get(1).getTag());
        assertEquals("90s", pedals.get(0).getTags().get(2).getTag());
        assertEquals("90s", pedals.get(0).getTags().get(3).getTag());
        assertEquals("lo-fi", pedals.get(0).getTags().get(4).getTag());
        assertEquals("lo-fi", pedals.get(0).getTags().get(5).getTag());
        assertEquals("lo-fi", pedals.get(0).getTags().get(6).getTag());
        assertEquals("lo-fi", pedals.get(0).getTags().get(7).getTag());
        assertEquals("reverb", pedals.get(0).getTags().get(8).getTag());
        assertEquals("reverb", pedals.get(0).getTags().get(9).getTag());
        assertEquals("reverb", pedals.get(0).getTags().get(10).getTag());
        assertEquals("reverb", pedals.get(0).getTags().get(11).getTag());
        assertEquals("shoegaze", pedals.get(0).getTags().get(12).getTag());
        assertEquals("shoegaze", pedals.get(0).getTags().get(13).getTag());
        assertEquals("shoegaze", pedals.get(0).getTags().get(14).getTag());
        assertEquals("shoegaze", pedals.get(0).getTags().get(15).getTag());
    }

    @Test
    void fetch_example_06() {
        // when pedals are filtered by two properties of an associated collection of guitarPedalTags
        // where the tag equals 'shoegaze'
        // and where the id is greater than or equal to 300
        // and the query is well-formed with two joins
        // and one join is for the fetch
        // and the other join is for both filters
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_06(300L, List.of("shoegaze"))
        );
        // then 1 pedal is returned
        // and the pedal has a tag named 'shoegaze'
        // and the pedal has tags with ids greater than or equal to 300
        // and the pedal is returned with 4 tags
        assertEquals(1, pedals.size());

        assertEquals(3L, pedals.get(0).getId());
        assertEquals("Soft Focus Reverb", pedals.get(0).getName());
        assertEquals("Catalinbread", pedals.get(0).getManufacturer().getName());

        assertEquals(4, pedals.get(0).getTags().size());
        pedals.get(0).getTags().sort(Comparator.comparing(GuitarPedalTag::getTag));
        assertEquals("90s", pedals.get(0).getTags().get(0).getTag());
        assertEquals("lo-fi", pedals.get(0).getTags().get(1).getTag());
        assertEquals("reverb", pedals.get(0).getTags().get(2).getTag());
        assertEquals("shoegaze", pedals.get(0).getTags().get(3).getTag());
    }

    @Test
    void fetch_example_07() {
        // when pedals are filtered by two properties of an associated collection of guitarPedalTags
        // where the tag equals 'shoegaze'
        // and where the id is greater than or equal to 300
        // and the query is well-formed,
        // but it only has one join for both the fetch and the filtering
        var pedals = guitarPedalRepository.findAll(
                guitarPedalSpecifications.fetch_example_07(300L, List.of("shoegaze"))
        );
        // then 1 pedal is returned
        // and the pedal has a tag named 'shoegaze'
        // and the pedal has tags with ids greater than or equal to 300
        // but the pedal is returned with only 1 tag
        assertEquals(1, pedals.size());

        assertEquals(3L, pedals.get(0).getId());
        assertEquals("Soft Focus Reverb", pedals.get(0).getName());
        assertEquals("Catalinbread", pedals.get(0).getManufacturer().getName());
        assertEquals(1, pedals.get(0).getTags().size());
        assertEquals("shoegaze", pedals.get(0).getTags().get(0).getTag());
    }
}
