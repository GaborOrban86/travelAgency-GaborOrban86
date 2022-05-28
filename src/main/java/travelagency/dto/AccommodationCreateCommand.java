package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Nights;
import travelagency.domain.enums.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AccommodationCreateCommand {

    @NotBlank(message = "Must be not blank!")
    private AccommodationType type;

    @NotBlank(message = "Must be not blank!")
    private Nights nights;

    @NotBlank(message = "Must be not blank!")
    private Service service;

    @NotBlank(message = "Must be not blank!")
    private double price;
}
