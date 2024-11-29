package com.kitsuno.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = KanjiIdValidator.class)
@Documented
public @interface ValidKanjiId {

    String message() default "Kanji ID cannot be null.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
