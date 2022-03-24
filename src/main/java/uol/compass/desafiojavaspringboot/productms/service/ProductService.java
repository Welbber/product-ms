package uol.compass.desafiojavaspringboot.productms.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import uol.compass.desafiojavaspringboot.productms.dto.ProductDto;
import uol.compass.desafiojavaspringboot.productms.entity.Product;
import uol.compass.desafiojavaspringboot.productms.repository.ProductRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper model;

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {
        try {
            log.info("Consultation of all products");
            List<Product> result = productRepository.findAll();

            log.info("mapping list ProductDto to Product and validating");
            return result.stream().map(product -> model.map(product, ProductDto.class)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("There was an error querying all products: ", e.getMessage());
        } finally {
            return new ArrayList<>();
        }
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                log.info("mapping ProductDto to Product found");
                return model.map(product, ProductDto.class);
            }
        } catch (Exception e) {
            log.error("There was an error querying products: ", e.getMessage());
        } finally {
            return null;
        }
    }

    public ProductDto save(ProductDto productDto) throws Exception {
        try {
            log.info("mapping ProductDto to Product and validating");
            @Valid
            Product cliente = model.map(productDto, Product.class);

            log.info("Save Product base");
            cliente = productRepository.save(cliente);

            log.info("Object successfully saved with id: {}", cliente.getId());
            return model.map(cliente, ProductDto.class);
        } catch (Exception e) {
            log.error("there was an error saving object: ", e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    public ProductDto update(ProductDto productDto, Long id) throws Exception {
        log.info("Consulting product with id = {}", id);
        try {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                log.info("updating product with id = {}", id);
                model.map(productDto, product.get());

                log.info("updating record in base");
                productRepository.save(product.get());

                log.info("mapping Product to ProductDto updating");
                return model.map(product, ProductDto.class);
            }
        } catch (Exception e) {
            log.error("there was an error updating object: ", e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            return null;
        }
    }

    public boolean delete(Long id) {
        try {
            log.info("querying product with id = {} to delete", id);
            Optional<Product> product = productRepository.findById(id);

            if (product.isPresent()) {
                log.info("deleting product with id = {}", id);
                productRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            log.error("There was an error deleting product");
        } finally {
            return false;
        }
    }
}
