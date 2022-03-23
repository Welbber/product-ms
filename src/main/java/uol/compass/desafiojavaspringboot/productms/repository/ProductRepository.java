package uol.compass.desafiojavaspringboot.productms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uol.compass.desafiojavaspringboot.productms.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
