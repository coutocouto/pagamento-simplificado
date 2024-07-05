package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.shared.domain.BaseId;

import java.util.Objects;
import java.util.UUID;

public class TransactionId extends BaseId {

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
