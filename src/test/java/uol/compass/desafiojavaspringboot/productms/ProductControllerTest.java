package uol.compass.desafiojavaspringboot.productms;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.hamcrest.Matchers.equalTo;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import io.restassured.http.ContentType;
import org.springframework.http.MediaType;
import uol.compass.desafiojavaspringboot.productms.controller.ProductController;
import uol.compass.desafiojavaspringboot.productms.dto.ProductDto;
import uol.compass.desafiojavaspringboot.productms.service.ProductService;

@WebMvcTest
public class ProductControllerTest {

    @Autowired
    private ProductController productController;

    @MockBean
    private ProductService productService;

    private ProductDto productTest;

    private static final String PRODUCTS_ROUTE = "/products";

    private static final String ID = "/{id}";

    private static String APPLICATION_JSON = String.valueOf(MediaType.APPLICATION_JSON);

    @BeforeEach
    public void setup() {
        standaloneSetup(this.productController);
        this.productTest = new ProductDto(1L, "Feijão", "Feijão carioquinha", 7.21);
    }

    @Test
    public void MustReturnSuccess_WhenSearchingForProduct() {

        when(this.productService.findById(1L)).thenReturn(this.productTest);

        given()
                .accept(ContentType.JSON)
                .when()
                .get(PRODUCTS_ROUTE + ID, 1L)
                .then()
                .body("name", equalTo(this.productTest.getName()))
                .body("description", equalTo(this.productTest.getDescription()))
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void MustReturnSuccess_WhenSearchingForAllProducts() {
        when(this.productService.findById(2L)).thenReturn(null);

        given()
                .accept(ContentType.JSON)
                .when()
                .get(PRODUCTS_ROUTE)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void MustReturnSuccess_WhenCreateProduct() {
        when(this.productService.save(this.productTest)).thenReturn(this.productTest);

        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .post(PRODUCTS_ROUTE)
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo(this.productTest.getName()))
                .body("description", equalTo(this.productTest.getDescription()));
    }

    @Test
    public void MustReturnSuccess_WhenUpdateProduct() {
        when(this.productService.update(this.productTest, 1L)).thenReturn(this.productTest);

        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .put(PRODUCTS_ROUTE + ID, 1L)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(this.productTest.getName()))
                .body("description", equalTo(this.productTest.getDescription()));
    }

    @Test
    public void MustReturnSuccess_WhenDeleteProduct() {

        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .put(PRODUCTS_ROUTE + ID, 1L)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void ShouldReturnError400_WhenSavingProductNameNullOrEmpty() {

        this.productTest.setName(null);
        given()
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .post(PRODUCTS_ROUTE)
                .then()
                .assertThat()
                .log()
                .ifValidationFails()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());


        this.productTest.setName("");
        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .post(PRODUCTS_ROUTE)
                .then()
                .log()
                .ifValidationFails()
                .assertThat()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void ShouldReturnError400_WhenSavingProductDescptionNullOrEmpty() {

        this.productTest.setDescription(null);
        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .post(PRODUCTS_ROUTE)
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        this.productTest.setDescription("");
        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .post(PRODUCTS_ROUTE)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void ShouldReturnError400_WhenSavingProductPriceNegativeOrNull() {

        this.productTest.setPrice(-1.0);
        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .post(PRODUCTS_ROUTE)
                .then()
                .log()
                .ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        this.productTest.setPrice(null);
        given()
                .accept(ContentType.JSON)
                .contentType(APPLICATION_JSON)
                .body(this.productTest)
                .when()
                .post(PRODUCTS_ROUTE)
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
