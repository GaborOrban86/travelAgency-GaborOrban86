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

    @NotBlank(message = "Must be not blank!")
    @Email
    private String email;

    @NotNull
    @Min(1922)
    @Max(2022)
    private LocalDate birthday;

    @NotBlank(message = "Must be not blank!")
    private Integer travelId;

}
