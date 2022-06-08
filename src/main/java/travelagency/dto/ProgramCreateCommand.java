package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class ProgramCreateCommand {

    @NotBlank(message = "Must be not blank!")
    private String name;

    @NotBlank(message = "Must be not blank!")
    private String description;

    @NotBlank(message = "Must be not blank!")
    private String guide;

    @NotNull(message = "Must be not null!")
    @Positive(message = "Must be a positive number!")
    private Integer price;

    @NotNull(message = "Must be not null!")
    private Integer travelId;
}
