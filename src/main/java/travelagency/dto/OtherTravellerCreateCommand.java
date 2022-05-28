package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.Destination;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class OtherTravellerCreateCommand {

    @NotBlank(message = "Must be not blank!")
    private String Name;

    @NotNull
    @Min(1922)
    @Max(2022)
    private Integer yearOfBirth;

    @NotBlank(message = "Must be not blank!")
    private Destination destination;
}
