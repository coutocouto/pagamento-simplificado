package br.com.pagamentos.simplificado.infrastructure.exception;

import br.com.pagamentos.simplificado.shared.domain.exceptions.EntityNotFoundException;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ErrorResponse;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ErrorResponseValidation;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandlerAdvice {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.of(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationErrors(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponseValidation.of(HttpStatus.BAD_REQUEST.value(), ex.getErrors()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleGenericErrors(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()));
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity handleGenericErrors(AuthorizationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.of(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DateEmptyValidation::new).toList());
    }

    private record DateEmptyValidation(String field, String message) {
        public DateEmptyValidation(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
