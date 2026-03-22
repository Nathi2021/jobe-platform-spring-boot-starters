package dev.jobe.platform.text.core.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lower {
    boolean enabled() default true;
}
