package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TravelCreateCommand {

    @NotNull(message = "Must be not null!")
    private Integer destinationId;

    @Future
    private LocalDate startDate;

    @Future
    private LocalDate endDate;
}
