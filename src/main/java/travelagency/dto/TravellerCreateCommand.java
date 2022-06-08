package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TravellerCreateCommand {

    @NotBlank(message = "Must be not blank!")
    private String Name;

    @Email
    private String email;

    @Past
    private LocalDate birthday;

    @NotNull(message = "Must be not null!")
    private Integer travelId;

}
