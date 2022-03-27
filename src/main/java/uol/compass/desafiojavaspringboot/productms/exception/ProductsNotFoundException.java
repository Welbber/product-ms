package uol.compass.desafiojavaspringboot.productms.exception;

public class ProductsNotFoundException extends RuntimeException {
    private String message;

    public ProductsNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ProductsNotFoundException() {
    }
}
