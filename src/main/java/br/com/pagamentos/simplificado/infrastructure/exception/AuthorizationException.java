package br.com.pagamentos.simplificado.infrastructure.exception;

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
