package br.com.pagamentos.simplificado.domain.user;

import br.com.pagamentos.simplificado.shared.domain.BaseId;

import java.util.Objects;
import java.util.UUID;

public class UserId extends BaseId {
    private final String value;

    private UserId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static UserId generate() {
        return UserId.from(UUID.randomUUID().toString().toLowerCase());
    }

    public static UserId from(final String anId) {
        return new UserId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

}
