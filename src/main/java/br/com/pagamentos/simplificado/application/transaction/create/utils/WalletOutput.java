package br.com.pagamentos.simplificado.application.transaction.create.utils;

import br.com.pagamentos.simplificado.domain.wallet.Wallet;

import java.time.Instant;

public record WalletOutput(
        String id,
        String accountType,
        double balance,
        Instant createdAt,
        UserOutput user

) {

    public static WalletOutput from(Wallet wallet) {
        String id = wallet.getId().getValue();
        String accountType = wallet.getAccountType().name();
        double balance = wallet.getBalance();
        Instant createdAt = wallet.getCreatedAt();
        UserOutput user = UserOutput.from(wallet.getUser());

        return new WalletOutput(id, accountType, balance, createdAt, user);
    }
}
