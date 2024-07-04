package br.com.pagamentos.simplificado.shared.domain.exceptions;

import java.util.List;
import java.util.Map;

public class NotificationException extends RuntimeException {
    private final Map<String, List<String>> errors;

    public NotificationException(Map<String, List<String>> errors) {
        super("Validation errors occurred.");
        this.errors = errors;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
