package br.com.pagamentos.simplificado.domain.user;

import br.com.pagamentos.simplificado.shared.domain.Uuid;

import java.util.Objects;
import java.util.UUID;

public class UserId extends Uuid {
    private final String value;

    private UserId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static UserId unique() {
        return UserId.from(UUID.randomUUID().toString().toLowerCase());
    }

    public static UserId from(final String anId) {
        return new UserId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final UserId that = (UserId) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
