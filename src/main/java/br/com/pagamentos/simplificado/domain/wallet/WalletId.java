package br.com.pagamentos.simplificado.domain.wallet;

import br.com.pagamentos.simplificado.domain.user.UserId;
import br.com.pagamentos.simplificado.shared.domain.Uuid;

import java.util.Objects;
import java.util.UUID;

public class WalletId extends Uuid {
    private final String value;

    private WalletId(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static WalletId generate() {
        return WalletId.from(UUID.randomUUID().toString().toLowerCase());
    }

    public static WalletId from(final String anId) {
        return new WalletId(anId);
    }

    @Override
    public String getValue() {
        return value;
    }
}
