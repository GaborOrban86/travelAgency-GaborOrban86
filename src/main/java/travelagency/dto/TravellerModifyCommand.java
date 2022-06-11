package travelagency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TravellerModifyCommand {

    @NotBlank(message = "Must be not blank!")
    @Schema(description = "Traveller name", example = "Tim Travel")
    private String name;

    @Email(message = "Must be a valid email format")
    @Schema(description = "Traveller email", example = "traveller@travel.com")
    private String email;
}
