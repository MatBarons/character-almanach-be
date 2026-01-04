package com.character_almanach.common.annotation.multiple_same_class;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoMultipleSameClassValidator.class)
@Documented
public @interface ValidCharacterClasses {

    String message() default "Only a single instance of each character class is allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
