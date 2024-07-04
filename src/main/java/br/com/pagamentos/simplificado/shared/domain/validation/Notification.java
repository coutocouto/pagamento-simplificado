package br.com.pagamentos.simplificado.shared.domain.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Notification {
    private Map<String, List<String>> errors = new HashMap<>();

    public void addError(String error, String field) {
        errors.computeIfAbsent(field, k -> new ArrayList<>()).add(error);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public Map<String, List<String>> getErrors() {
        return new HashMap<>(errors);
    }

    public void copyErrors(Notification notification) {
        notification.getErrors().forEach(this::setError);
    }

    private void setError(String field, List<String> error) {
        errors.put(field, error);
    }

    public String toJSON() {
        return errors.toString();
    }
}
