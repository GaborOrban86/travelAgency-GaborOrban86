package travelagency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TravelModifyCommand {

    @Future(message = "Must be a future date!")
    @Schema(description = "Start date of travel", example = "2022-09-10")
    private LocalDate startDate;

    @Future(message = "Must be a future date!")
    @Schema(description = "End date of travel", example = "2022-09-12")
    private LocalDate endDate;
}
