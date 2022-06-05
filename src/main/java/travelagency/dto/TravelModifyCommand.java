package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TravelModifyCommand {
    @NotBlank(message = "Must be not blank!")
    @Future
    private LocalDate startDate;

    @NotBlank(message = "Must be not blank!")
    @Future
    private LocalDate endDate;
}
