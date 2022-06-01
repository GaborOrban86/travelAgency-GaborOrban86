package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DestinationCreateCommand {

    @NotBlank(message = "Must be not blank.")
    private String name;

    @NotNull
    private Integer price;
}
