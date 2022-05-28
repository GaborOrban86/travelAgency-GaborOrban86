package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.Destination;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TravelCreateCommand {

    @NotBlank(message = "Must be not blank!")
    private Destination destination;
}
