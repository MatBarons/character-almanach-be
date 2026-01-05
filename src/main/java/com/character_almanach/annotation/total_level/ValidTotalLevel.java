package com.character_almanach.annotation.total_level;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TotalLevelMatchesClassesValidator.class)
@Documented
public @interface ValidTotalLevel {

    String message() default "Total level must equal the sum of class levels";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
