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
public class ProgramCreateCommand {

    @NotBlank(message = "Must be not blank!")
    private String name;

    @NotBlank(message = "Must be not blank!")
    private Destination city;

    @NotBlank(message = "Must be not blank!")
    private String description;

    @NotNull(message = "Must be not null!")
    private double price;

    @NotBlank(message = "Must be not blank!")
    private String guide;

    @NotNull(message = "Must be not null!")
    @Min(0)
    private double minimumNumberOfParticipants;

    @NotNull(message = "Must be not null!")
    @Max(20)
    private double maximumNumberOfParticipants;
}
