package org.quinnandrews.spring.data.specification.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Convenient utility methods for making Specifications.
 *
 * @author Quinn Andrews
 */
public class SpecificationUtil {

    private SpecificationUtil() {
        // no-op
    }

    public static String toLowerCase(final String string) {
        return StringUtils.lowerCase(string);
    }

    public static Object stripToNull(final Object object) {
        return object instanceof String ? StringUtils.stripToNull(object.toString()) : object;
    }

    public static String escapeWildcardCharacters(final String string) {
        return StringUtils.replaceEach(string, new String[]{"%", "_"}, new String[]{"\\%", "\\_"});
    }

    public static boolean isWildcardExpression(final String string) {
        return StringUtils.containsAny(StringUtils.stripToNull(string), "_%");
    }

    public static boolean isEmptyWildcardExpression(final String string) {
        return StringUtils.containsOnly(StringUtils.stripToNull(string), "_%");
    }

    public static boolean noneAreNull(final Object... objects) {
        return Arrays.stream(objects).noneMatch(obj -> Objects.isNull(stripToNull(obj)));
    }
}
