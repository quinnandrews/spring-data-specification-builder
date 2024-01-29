package io.github.quinnandrews.spring.data.specification.annotations;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * An alias of {@link Component @Component} indicating that the
 * annotated class contains Specifications. Complements
 * {@link org.springframework.stereotype.Controller @Controller},
 * {@link org.springframework.stereotype.Service @Service}, and
 * {@link org.springframework.stereotype.Repository @Repository}.
 *
 * @author Quinn Andrews
 * @see Component
 * @see org.springframework.stereotype.Controller
 * @see org.springframework.stereotype.Service
 * @see org.springframework.stereotype.Repository
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Specifications {

    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any (or empty String otherwise)
     */
    @AliasFor(annotation = Component.class)
    String value() default "";

}