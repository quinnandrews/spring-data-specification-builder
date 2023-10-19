package org.quinnandrews.spring.data.specification.builder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpecificationUtilTest {

    @Test
    void toLowerCase_returnsLowerCasedString() {
        var string = " Xy 12Z ";
        assertEquals(" xy 12z ", SpecificationUtil.toLowerCase(string));
    }

    @Test
    void toLowerCase_isNullSafe() {
        assertNull(SpecificationUtil.toLowerCase(null));
    }

    @Test
    void stripToNull_returnsStrippedString() {
        var string = " Xy 12Z \t \n ";
        assertEquals("Xy 12Z", SpecificationUtil.stripToNull(string));
    }

    @Test
    void stripToNull_returnsNullWhenBlank() {
        var string = "";
        assertNull(SpecificationUtil.stripToNull(string));
        string = " \t \n ";
        assertNull(SpecificationUtil.stripToNull(string));
    }

    @Test
    void stripToNull_returnsOriginalObjectWhenNotString() {
        var object = 5;
        assertEquals(5, SpecificationUtil.stripToNull(object));
    }


    @Test
    void stripToNull_isNullSafe() {
        assertNull(SpecificationUtil.stripToNull(null));
    }

    @Test
    void escapeWildcardCharacters_returnsEscapedString() {
        var string = " Xy_12Z%* ";
        assertEquals(" Xy\\_12Z\\%* ", SpecificationUtil.escapeWildcardCharacters(string));
    }

    @Test
    void escapeWildcardCharacters_isNullSafe() {
        assertNull(SpecificationUtil.escapeWildcardCharacters(null));
    }

    @Test
    void isWildcardExpression_returnsTrueWhenContainsPercent() {
        assertTrue(SpecificationUtil.isWildcardExpression("Xy 12Z%"));
    }

    @Test
    void isWildcardExpression_returnsTrueWhenContainsUnderscore() {
        assertTrue(SpecificationUtil.isWildcardExpression("Xy_12Z "));
    }

    @Test
    void isWildcardExpression_returnsFalseWhenNull() {
        assertFalse(SpecificationUtil.isWildcardExpression(null));
    }

    @Test
    void isWildcardExpression_returnsFalseWhenContainsNeitherPercentNorUnderscore() {
        assertFalse(SpecificationUtil.isWildcardExpression("Xy 12Z *"));
    }

    @Test
    void isEmptyWildcardExpression_returnsTrueWhenContainsOnlyPercent() {
        assertTrue(SpecificationUtil.isEmptyWildcardExpression("%"));
    }

    @Test
    void isEmptyWildcardExpression_returnsTrueWhenContainsWhitespaceAndPercent() {
        assertTrue(SpecificationUtil.isEmptyWildcardExpression(" \t%\n "));
    }

    @Test
    void isEmptyWildcardExpression_returnsTrueWhenContainsOnlyUnderscore() {
        assertTrue(SpecificationUtil.isEmptyWildcardExpression("_"));
    }

    @Test
    void isEmptyWildcardExpression_returnsTrueWhenContainsWhitespaceAndUnderscore() {
        assertTrue(SpecificationUtil.isEmptyWildcardExpression(" \t_\n "));
    }

    @Test
    void isEmptyWildcardExpression_returnsTrueWhenContainBothPercentAndUnderscore() {
        assertTrue(SpecificationUtil.isEmptyWildcardExpression("_%"));
    }

    @Test
    void isEmptyWildcardExpression_returnsTrueWhenContainsWhitespaceWithUnderscoreAndPercent() {
        assertTrue(SpecificationUtil.isEmptyWildcardExpression(" \t_%\n "));
    }

    @Test
    void isEmptyWildcardExpression_returnsFalseWhenContainsWhitespaceBetweenUnderscoreAndPercent() {
        assertFalse(SpecificationUtil.isEmptyWildcardExpression("_ %"));
    }

    @Test
    void isEmptyWildcardExpression_returnsFalseWhenNull() {
        assertFalse(SpecificationUtil.isEmptyWildcardExpression(null));
    }

    @Test
    void isEmptyWildcardExpression_returnsFalseWhenBlank() {
        assertFalse(SpecificationUtil.isEmptyWildcardExpression(""));
    }

    @Test
    void isEmptyWildcardExpression_returnsFalseWhenContainsNonWhiteSpaceCharacters() {
        assertFalse(SpecificationUtil.isEmptyWildcardExpression("Xy12Z_%"));
    }

    @Test
    void isEmptyWildcardExpression_returnsFalseWhenContainsAsterisk() {
        assertFalse(SpecificationUtil.isEmptyWildcardExpression("_%*"));
    }

    @Test
    void isEmptyWildcardExpression_returnsFalseWhenCharacterPresentButContainsNoPercentOrUnderscore() {
        assertFalse(SpecificationUtil.isEmptyWildcardExpression("Xy12Z"));
    }

    @Test
    void notNull_returnsFalseWhenArgumentIsNull() {
        assertFalse(SpecificationUtil.noneAreNull((Object) null));
    }

    @Test
    void notNull_returnsFalseWhenAnyArgumentIsNull() {
        assertFalse(SpecificationUtil.noneAreNull("Xy12Z", null));
    }

    @Test
    void notNull_returnsTrueWhenArgumentIsNotNull() {
        assertTrue(SpecificationUtil.noneAreNull("Xy12Z"));
    }

    @Test
    void notNull_returnsTrueWhenAllArgumentAreNotNull() {
        assertTrue(SpecificationUtil.noneAreNull("Xy12Z", 5));
    }
}