package br.com.pagamentos.simplificado.shared.domain.exceptions;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class ErrorResponseValidation {
    private final int status;
    private final String timestamp;
    private final Map<String, List<String>> message;

    private ErrorResponseValidation(int status, Map<String, List<String>> message) {
        this.status = status;
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.message = message;
    }

    public static ErrorResponseValidation of(int status, Map<String, List<String>> message) {
        return new ErrorResponseValidation(status, message);
    }

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public Map<String, List<String>> getMessage() {
        return message;
    }

}