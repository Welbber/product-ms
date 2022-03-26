package uol.compass.desafiojavaspringboot.productms.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @NotBlank(message = "{name.not.blank}")
    private String name;

    @NotBlank(message = "{description.not.blank}")
    private String description;

    @Min(value = 0, message = "{price.min}")
    @NotNull(message = "{price.not.null}")
    private Double price;
}