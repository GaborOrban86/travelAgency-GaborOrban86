package travelagency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelModifyCommand {

    @Future(message = "Must be a future date!")
    @Schema(description = "Start date of travel", example = "2023-09-10")
    private LocalDate startDate;

    @Future(message = "Must be a future date!")
    @Schema(description = "End date of travel", example = "2023-09-12")
    private LocalDate endDate;
}
