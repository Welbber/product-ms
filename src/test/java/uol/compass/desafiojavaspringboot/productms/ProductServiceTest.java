package uol.compass.desafiojavaspringboot.productms;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uol.compass.desafiojavaspringboot.productms.dto.ProductDto;
import uol.compass.desafiojavaspringboot.productms.entity.Product;
import uol.compass.desafiojavaspringboot.productms.repository.ProductRepository;
import uol.compass.desafiojavaspringboot.productms.service.ProductService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Spy
    ModelMapper model;

    @MockBean
    ProductRepository productRepository;

    private ProductDto productDtoTest;

    private Product productTest;

    @BeforeEach
    public void setup() {
        this.productDtoTest = new ProductDto(1L, "Feijão", "Feijão carioquinha", 7.21);
        this.productTest = new Product(1L, "Feijão", "Feijão carioquinha", 7.21);
    }

    @Test
    public void MustReturnObjectProductDto_WhenSaveObjectProduct() {
        when(this.productRepository.save(this.productTest)).thenReturn(this.productTest);
        when(this.model.map(this.productDtoTest, Product.class)).thenReturn(this.productTest);
        ProductDto productTest = productService.save(this.productDtoTest);

        AssertColumnProduct(this.productDtoTest, productTest);
    }

    @Test
    public void MustProductsAlreadyExistsException_WhenSaveProduct() {
        when(this.productRepository.findByNameAndDescription(productDtoTest.getName(), productDtoTest.getDescription())).thenReturn(Optional.of(this.productTest));
        when(this.productRepository.findById(1L)).thenReturn(Optional.of(this.productTest));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.productService.save(this.productDtoTest);
        });

        Assert.assertEquals(ProductService.MESSAGE_EXCEPTION_ALREADY_EXOSTS, exception.getMessage());
    }

    @Test
    public void MustProductAlreadyExists_WhenUpdateProduct() {
        when(this.productRepository.findById(1L)).thenReturn(Optional.ofNullable(this.productTest));
        ProductDto productTest = this.productService.findById(1L);
        AssertColumnProduct(this.productDtoTest, productTest);
    }

    @Test
    public void MustProductsNotFoundException_WhenConsultIdProduct() {

        when(this.productRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.productService.findById(1L);
        });

        Assert.assertEquals(ProductService.MESSAGE_EXCEPTION_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void MustProductsNotFoundException_WhenUpdateProduct() {

        when(this.productRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.productService.update(this.productDtoTest, 1L);
        });

        Assert.assertEquals(ProductService.MESSAGE_EXCEPTION_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void MustReturnObjectProductDto_WhenUpdateProduct() {

        when(this.productRepository.findById(1L)).thenReturn(Optional.ofNullable(this.productTest));

        this.productDtoTest.setPrice(15.0);
        when(this.productRepository.save(this.productTest)).thenReturn(this.productTest);

        ProductDto produtDtoTest = this.productService.update(this.productDtoTest, 1L);

        AssertColumnProduct(produtDtoTest, this.productDtoTest);
    }

    @Test
    public void MustProductsAlreadyExistsException_WhenUpadeteProduct() {
        when(this.productRepository.findByNameAndDescription(productDtoTest.getName(), productDtoTest.getDescription())).thenReturn(Optional.of(this.productTest));
        when(this.productRepository.findById(1L)).thenReturn(Optional.ofNullable(this.productTest));
        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.productService.update(this.productDtoTest, 1L);
        });

        Assert.assertEquals(ProductService.MESSAGE_EXCEPTION_ALREADY_EXOSTS, exception.getMessage());
    }

    private void AssertColumnProduct(ProductDto productDto, ProductDto productDtoExepct) {
        Assert.assertEquals(productDtoExepct.getName(), productDto.getName());
        Assert.assertEquals(productDtoExepct.getDescription(), productDto.getDescription());
        Assert.assertEquals(productDtoExepct.getPrice(), productDto.getPrice());
    }
}