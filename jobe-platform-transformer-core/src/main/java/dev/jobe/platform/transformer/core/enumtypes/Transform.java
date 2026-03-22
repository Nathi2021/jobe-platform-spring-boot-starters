package dev.jobe.platform.transformer.core.enumtypes;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Transform {
    CaseType value() default CaseType.NONE;
}