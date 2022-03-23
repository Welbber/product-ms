package uol.compass.desafiojavaspringboot.productms.entity;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Table(name = "Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PRIVATE)
    private Long id;

    @NotBlank(message = "The name field needs to be filled")
    private String name;

    @NotBlank(message = "The description field needs to be filled")
    private String description;

    @NotBlank(message = "The price field needs to be filled")
    @Min(value = 0)
    private Double price;
}