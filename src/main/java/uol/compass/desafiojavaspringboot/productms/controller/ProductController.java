package uol.compass.desafiojavaspringboot.productms.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@CrossOrigin(origins = "*")
@Api(value = "Product-ms")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "Retorna uma lista de Produtos")
    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }


    @ApiOperation(value = "Retorna um produto a partir do if informado Path Param")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable long id) {
        return new ResponseEntity<>(productService.findById(id), HttpStatus.OK);
    }


    @ApiOperation(value = "Retorna uma lista de produtos que atendem as condições da consulta informada")
    @GetMapping(value = "/search")
    public ResponseEntity findByQueryParameters(@RequestParam(value = "q", required = false) String q,
                                                @RequestParam(value = "min_price", required = false) Double minPrice,
                                                @RequestParam(value = "max_price", required = false) Double maxPrice) {

        return new ResponseEntity<>(productService.findByQueryParameters(q, minPrice, maxPrice), HttpStatus.OK);
    }


    @ApiOperation(value = "Cadastra um produto")
    @PostMapping
    public ResponseEntity create(@RequestBody @Valid ProductDto product) {
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }


    @ApiOperation(value = "Atualiza um produto")
    @PutMapping(value = "{id}")
    public ResponseEntity update(@RequestBody @Valid ProductDto pŕoduct, @PathVariable long id) {
        return new ResponseEntity<>(productService.update(pŕoduct, id), HttpStatus.OK);
    }


    @ApiOperation(value = "Deleta um produto")
    @DeleteMapping(value = "{id}")
    public ResponseEntity delete(@PathVariable long id) {
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}