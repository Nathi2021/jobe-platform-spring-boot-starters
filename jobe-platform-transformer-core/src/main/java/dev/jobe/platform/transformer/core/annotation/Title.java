package dev.jobe.platform.transformer.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Title {
    boolean enabled() default true;
}
