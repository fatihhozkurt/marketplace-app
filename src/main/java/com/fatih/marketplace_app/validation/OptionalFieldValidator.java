package com.fatih.marketplace_app.validation;

import com.fatih.marketplace_app.annotation.OptionalFieldValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for {@link OptionalFieldValidation} annotation.
 * Validates an optional string field based on constraints such as blank check, length, and pattern matching.
 */
public class OptionalFieldValidator implements ConstraintValidator<OptionalFieldValidation, String> {

    private boolean notBlank;
    private int min;
    private int max;
    private String pattern;

    /**
     * Initializes the validator with the annotation constraints.
     *
     * @param constraintAnnotation the annotation instance containing validation constraints
     */
    @Override
    public void initialize(OptionalFieldValidation constraintAnnotation) {

        this.notBlank = constraintAnnotation.notBlank();
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
        this.pattern = constraintAnnotation.pattern();
    }

    /**
     * Validates the given string value based on the defined constraints.
     *
     * @param value   the string value to validate
     * @param context the validation context
     * @return {@code true} if the value is valid, {@code false} otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        //Null check
        if (value == null) {
            return true;
        }

        //Whitespace check
        if (notBlank && value.isBlank()) {
            return false;
        }

        //Length check
        if (value.length() < min || value.length() > max) {
            return false;
        }

        //
        return value.matches(pattern);
    }
}
