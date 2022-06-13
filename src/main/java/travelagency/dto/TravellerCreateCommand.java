package travelagency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravellerCreateCommand {

    @NotBlank(message = "Must be not blank!")
    @Schema(description = "Traveller name", example = "Tim Travel")
    private String name;

    @Email(message = "Must be a valid email format")
    @Schema(description = "Traveller email", example = "traveller@travel.com")
    private String email;

    @Past(message = "Must be a past date")
    @Schema(description = "Traveller Birthday", example = "1986-06-09")
    private LocalDate birthday;

    @NotNull(message = "Must be not null!")
    @Schema(description = "ID of travel", example = "1")
    private Integer travelId;

}
