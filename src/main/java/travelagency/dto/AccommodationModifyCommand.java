package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Catering;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class AccommodationModifyCommand {
    @NotBlank(message = "Must be not blank!")
    private String name;

    @NotBlank(message = "Must be not blank!")
    private AccommodationType type;

    @NotBlank(message = "Must be not blank!")
    private Catering catering;

    @NotBlank(message = "Must be not blank!")
    private double price;
}
