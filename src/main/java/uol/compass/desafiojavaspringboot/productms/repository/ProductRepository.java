package uol.compass.desafiojavaspringboot.productms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import uol.compass.desafiojavaspringboot.productms.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    public Optional<Product> findByNameAndDescription(final String name, final String description);
}