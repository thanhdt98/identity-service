package com.thanhxv.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * explain
 * DobConstraint : @interface
 * LocalDate : type data validation
 */
public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
    private int min;

    /**
     * explain
     * 1. duoc execute truoc method boolean isValid(LocalDate value, ConstraintValidatorContext context)
     * 2. moi khi constraint duoc khoi tao co the lay duoc data cua annotation tu method nay
     * ex: @DobConstraint(min = 18) => co the lay duoc gia tri 18 duoc set trong annotation
     * @param constraintAnnotation annotation instance for a given constraint declaration
     */
    @Override
    public void initialize(DobConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    /**
     * explain method xu ly data co dung hay k?
     * best practice moi annotation chi nen chiu trach nhiem cho 1 validation
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        long year = ChronoUnit.YEARS.between(value, LocalDate.now());

        return year >= min;
    }
}
