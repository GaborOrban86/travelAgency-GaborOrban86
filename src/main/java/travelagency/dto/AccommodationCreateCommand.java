package travelagency.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.validation.Catering;
import travelagency.validation.Type;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationCreateCommand {

    @NotBlank(message = "Must be not blank!")
    @Schema(description = "Accommodation name", example = "Hilton Hotel")
    private String name;

    @Type
    @Schema(description = "Type of Accommodation", example = "SOLO")
    private String type;

    @Catering
    @Schema(description = "Catering of Accommodation", example = "FULL")
    private String catering;

    @NotNull(message = "Must be not null!")
    @Positive(message = "Must be a positive number!")
    @Schema(description = "ID of travel", example = "1")
    private Integer travelId;

    @NotNull(message = "Must be not null!")
    @Positive(message = "Must be a positive number!")
    @Schema(description = "Price of travel", example = "35000")
    private Integer price;

}
