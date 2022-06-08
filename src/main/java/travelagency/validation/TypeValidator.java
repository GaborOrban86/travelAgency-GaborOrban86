package travelagency.validation;

import travelagency.domain.enums.AccommodationType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TypeValidator implements ConstraintValidator<Type, String> {
    private final List<String> validValues = Stream.of(AccommodationType.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    @Override
    public void initialize(Type constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !string.isBlank() && validValues.contains(string);
    }
}
