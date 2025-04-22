package co.edu.uceva.facultadmicroservice.domain.exception;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {
    public final BindingResult result;
    public ValidationException(BindingResult result) {
        super("Error De Validacion De Datos.");
        this.result = result;
    }
}