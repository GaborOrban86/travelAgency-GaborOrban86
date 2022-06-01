package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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
    private Integer yearOfBirth;

    @NotBlank(message = "Must be not blank!")
    private String travelId;

}
