package uol.compass.desafiojavaspringboot.productms.controller;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uol.compass.desafiojavaspringboot.productms.entity.ErrorMessage;
import uol.compass.desafiojavaspringboot.productms.exception.ProductAlreadyExistsException;
import uol.compass.desafiojavaspringboot.productms.exception.ProductsNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final int FIRST_ERROR_INDEX = 0;

    @ExceptionHandler(ProductsNotFoundException.class)
    public ResponseEntity handleProductNotFoundException(ProductsNotFoundException productsNotFoundException) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(productsNotFoundException.getMessage());
        error.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity handleProductAlreadyExists(ProductAlreadyExistsException productAlreadyExistsException) {
        ErrorMessage error = new ErrorMessage();
        error.setMessage(productAlreadyExistsException.getMessage());
        error.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorMessage errorDetails = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), errorList.get(FIRST_ERROR_INDEX));
        return handleExceptionInternal(ex, errorDetails, headers, HttpStatus.BAD_REQUEST, request);
    }

}


