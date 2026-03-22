package dev.jobe.platform.text.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sentence {
    boolean enabled() default true;
}
