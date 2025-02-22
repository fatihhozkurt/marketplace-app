package com.fatih.marketplace_app.annotation;

import com.fatih.marketplace_app.validation.OptionalFieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OptionalFieldValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OptionalFieldValidation {

    String message() default "Invalid value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean notBlank() default false;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    String pattern() default ".*";
}
