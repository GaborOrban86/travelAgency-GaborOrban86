package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class TravellerModifyCommand {

    @NotBlank(message = "Must be not blank!")
    private String Name;

    @NotBlank(message = "Must be not blank!")
    @Email
    private String email;
}
