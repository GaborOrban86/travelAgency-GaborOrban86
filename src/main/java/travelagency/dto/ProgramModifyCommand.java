package travelagency.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProgramModifyCommand {
    @NotBlank(message = "Must be not blank!")
    private String name;

    @NotBlank(message = "Must be not blank!")
    private String description;

    @NotNull(message = "Must be not null!")
    private double price;

    @NotBlank(message = "Must be not blank!")
    private String guide;
}