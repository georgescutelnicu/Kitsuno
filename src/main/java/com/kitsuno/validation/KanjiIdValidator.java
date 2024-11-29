package com.kitsuno.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class KanjiIdValidator implements ConstraintValidator<ValidKanjiId, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        String method = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getMethod();

        if ("PUT".equals(method)) {
            return true;
        }

        return value != null;
    }
}
