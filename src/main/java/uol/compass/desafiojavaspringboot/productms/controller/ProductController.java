package uol.compass.desafiojavaspringboot.productms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uol.compass.desafiojavaspringboot.productms.dto.ProductDto;
import uol.compass.desafiojavaspringboot.productms.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    public ResponseEntity findByQueryParameters(@RequestParam(value = "q", required = false) String q,
                                                @RequestParam(value = "min_price", required = false) Double minPrice,
                                                @RequestParam(value = "max_price", required = false) Double maxPrice) {

        return new ResponseEntity<>(productService.findByQueryParameters(q, minPrice, maxPrice), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid ProductDto product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity update(@RequestBody @Valid ProductDto pŕoduct, @PathVariable long id) {
        return new ResponseEntity<>(productService.update(pŕoduct, id), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}