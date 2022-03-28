package uol.compass.desafiojavaspringboot.productms.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    private String message;

    public ProductAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public ProductAlreadyExistsException() {
    }
}
