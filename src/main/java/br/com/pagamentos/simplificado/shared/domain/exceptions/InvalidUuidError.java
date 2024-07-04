package br.com.pagamentos.simplificado.shared.domain.exceptions;

public class InvalidUuidError extends RuntimeException {
    public InvalidUuidError() {
        super("ID must be a valid UUID");
    }

    public InvalidUuidError(String message) {
        super(message);
    }
}