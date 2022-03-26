package uol.compass.desafiojavaspringboot.productms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @NotBlank(message = "{description.not.blank}")
    private String description;

    @Min(value = 0, message = "{price.min}")
    @NotNull(message = "{price.not.null}")
    private Double price;
}
