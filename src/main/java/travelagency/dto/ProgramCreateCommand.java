package travelagency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCreateCommand {

    @NotBlank(message = "Must be not blank!")
    @Schema(description = "Program name", example = "Hide and seek")
    private String name;

    @NotBlank(message = "Must be not blank!")
    @Schema(description = "Description of program", example = "Funny game")
    private String description;

    @NotBlank(message = "Must be not blank!")
    @Schema(description = "Guide of program", example = "Peter Program")
    private String guide;

    @NotNull(message = "Must be not null!")
    @Positive(message = "Must be a positive number!")
    @Schema(description = "Price of program", example = "30000")
    private Integer price;

    @NotNull(message = "Must be not null!")
    @Schema(description = "ID of travel", example = "1")
    private Integer travelId;
}
