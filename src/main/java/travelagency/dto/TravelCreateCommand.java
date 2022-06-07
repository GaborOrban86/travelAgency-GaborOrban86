package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TravelCreateCommand {

    @NotBlank(message = "Must be not blank!")
    private Integer destinationId;

    @NotBlank(message = "Must be not blank!")
    @Future
    private LocalDate startDate;

    @NotBlank(message = "Must be not blank!")
    @Future
    private LocalDate endDate;
}
