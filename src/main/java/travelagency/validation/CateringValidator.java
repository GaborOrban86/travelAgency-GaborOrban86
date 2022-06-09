package travelagency.validation;

import travelagency.domain.enums.AccommodationCatering;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CateringValidator implements ConstraintValidator<Catering, String> {
    private final List<String> validValues = Stream.of(AccommodationCatering.values())
            .map(Enum::name)
            .collect(Collectors.toList());

    @Override
    public void initialize(Catering constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !string.isBlank() && validValues.contains(string);
    }
}
