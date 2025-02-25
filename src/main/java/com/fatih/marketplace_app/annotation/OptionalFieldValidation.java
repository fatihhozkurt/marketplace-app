package com.fatih.marketplace_app.annotation;

import com.fatih.marketplace_app.validation.OptionalFieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom annotation for optional field validation.
 * Supports constraints like non-blank check, length limits, and regex patterns.
 */
@Documented
@Constraint(validatedBy = OptionalFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalFieldValidation {

    /** Validation error message. */
    String message() default "Invalid value.";

    /** Validation groups. */
    Class<?>[] groups() default {};

    /** Custom payload objects. */
    Class<? extends Payload>[] payload() default {};

    /** Ensures the field is not blank if set to true. */
    boolean notBlank() default false;

    /** Minimum length constraint. */
    int min() default 0;

    /** Maximum length constraint. */
    int max() default Integer.MAX_VALUE;

    /** Regex pattern for validation. */
    String pattern() default ".*";
}
