package br.com.pagamentos.simplificado.shared.domain.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorResponse {
    private final int status;
    private final String timestamp;
    private final String message;

    private ErrorResponse(int status, String message) {
        this.status = status;
        this.timestamp = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
        this.message = message;
    }

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message);
    }

    public int getStatus() {
        return status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }
}