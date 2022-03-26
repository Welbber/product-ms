package uol.compass.desafiojavaspringboot.productms.service;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import uol.compass.desafiojavaspringboot.productms.dto.ProductDto;
import uol.compass.desafiojavaspringboot.productms.entity.Product;
import uol.compass.desafiojavaspringboot.productms.repository.ProductRepository;
import uol.compass.desafiojavaspringboot.productms.repository.specification.ProductSpecification;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper model;

    @Transactional(readOnly = true)
    public List<ProductDto> findAll() {

        log.info("Consultation of all products");
        List<Product> result = productRepository.findAll();

        log.info("mapping list ProductDto to Product and validating");
        return result.stream().map(product -> model.map(product, ProductDto.class)).collect(Collectors.toList());

    }

    public List<ProductDto> findByQueryParameters(final String q, Double minPrice, Double maxPrice) {

        var result = productRepository.findAll(where(ProductSpecification.createSpecification(q, minPrice, maxPrice)));

        return result.stream().map(product -> model.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) throws Exception {

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            log.info("mapping ProductDto to Product found");
            return model.map(product.get(), ProductDto.class);
        } else {
            throw new Exception("{product.not.found}");
        }
    }

    public ProductDto save(ProductDto productDto) throws Exception {

        log.info("mapping ProductDto to Product and validating");

        Product product = model.map(productDto, Product.class);

        log.info("Save Product base");
        product = productRepository.save(product);

        log.info("Object successfully saved with id: {}", product.getId());
        return model.map(product, ProductDto.class);

    }

    public ProductDto update(ProductDto productDto, Long id) throws Exception {
        log.info("Consulting product with id = {}", id);

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            log.info("updating product with id = {}", id);
            model.map(productDto, product.get());

            log.info("updating record in base");
            productRepository.save(product.get());

            log.info("mapping Product to ProductDto updating");
            return model.map(product.get(), ProductDto.class);
        } else
            throw new Exception("{product.not.found}");

    }

    public boolean delete(Long id) {

        log.info("querying product with id = {} to delete", id);
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            log.info("deleting product with id = {}", id);
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
