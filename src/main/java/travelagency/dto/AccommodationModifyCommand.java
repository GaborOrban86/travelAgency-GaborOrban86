package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Catering;
import travelagency.validation.EnumNamePattern;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class AccommodationModifyCommand {
    @NotBlank(message = "Must be not blank!")
    private String name;

    @EnumNamePattern(regexp = "SOLO|COUPLE|FAMILY",message = "Must be SOLO, COUPLE or FAMILY!")
    private AccommodationType type;

    @EnumNamePattern(regexp = "FULL|HALF|NOTHING",message = "Must be FULL, HALF or NOTHING!")
    private Catering catering;

    @NotNull(message = "Must be not null!")
    @Positive(message = "Must be a positive number!")
    private Integer price;
}
