package io.github.quinnandrews.spring.data.specification.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Convenient utility methods for making Specifications.
 *
 * @author Quinn Andrews
 */
public class SpecificationUtil {

    /**
     * Default Constructor. Private since this Class is not
     * meant to be instantiated.
     */
    private SpecificationUtil() {
        // no-op
    }

    /**
     * Converts any upper case characters in the given String
     * to lower case characters and returns a new String. Returns
     * null if the given String is null.
     *
     * @param string The String to convert.
     * @return A new converted String or null if the given String
     *         is null.
     */
    public static String toLowerCase(final String string) {
        return StringUtils.lowerCase(string);
    }

    /**
     * If the given Object is a String, then all leading and trailing
     * whitespace characters are removed and a new String is returned.
     * Otherwise, if the given Object is not a String, then the given
     * Object is simply returned without any modifications. Alternatively,
     * if the given Object is null, then null is returned.
     *
     * @param object The Object to check.
     * @return Object that is either a stripped String, the original
     *         Object or null.
     */
    public static Object stripToNull(final Object object) {
        return object instanceof String ? StringUtils.stripToNull(object.toString()) : object;
    }

    /**
     * Escapes any SQL wildcard characters in the given String and
     * returns a new String. Returns null if the given String is null.
     *
     * @param string The String to convert.
     * @return A new converted String or null if the given String
     *         is null.
     */
    public static String escapeWildcardCharacters(final String string) {
        return StringUtils.replaceEach(string, new String[]{"%", "_"}, new String[]{"\\%", "\\_"});
    }

    /**
     * Returns true if the given String, when stripped of leading and
     * trailing whitespace, contains any SQL wildcard characters.
     *
     * @param string The String to check.
     * @return Boolean indicating whether the given String contains any
     *         SQL wildcard characters.
     */
    public static boolean isWildcardExpression(final String string) {
        return StringUtils.containsAny(StringUtils.stripToNull(string), "_%");
    }

    /**
     * Returns true if the given String, when stripped of leading and
     * trailing whitespace, contains only SQL wildcard characters.
     *
     * @param string The String to check.
     * @return Boolean indicating whether the given String contains only
     *         SQL wildcard characters.
     */
    public static boolean isEmptyWildcardExpression(final String string) {
        return StringUtils.containsOnly(StringUtils.stripToNull(string), "_%");
    }

    /**
     * Returns true if all given Objects are null.
     *
     * @param objects The Objects to check.
     * @return Boolean indicating whether all given Objects are null.
     */
    public static boolean noneAreNull(final Object... objects) {
        return Arrays.stream(objects).noneMatch(obj -> Objects.isNull(stripToNull(obj)));
    }
}
