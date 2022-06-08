package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TravelModifyCommand {

    @Future
    private LocalDate startDate;

    @Future
    private LocalDate endDate;
}
