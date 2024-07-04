package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.domain.user.UserId;
import br.com.pagamentos.simplificado.shared.domain.Uuid;

import java.util.Objects;
import java.util.UUID;

public class TransactionId extends Uuid {
    private final String value;

    private TransactionId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static TransactionId generate() {
        return TransactionId.from(UUID.randomUUID().toString().toLowerCase());
    }

    public static TransactionId from(final String anId) {
        return new TransactionId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }
}
