package br.com.pagamentos.simplificado.application.transaction.create;

import br.com.pagamentos.simplificado.application.transaction.create.utils.WalletOutput;
import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;

import java.time.Instant;

public record CreateTransactionOutput(
        String id,
        WalletOutput payee,
        WalletOutput payer,
        double amount,
        Instant createdAt,
        TransactionStatus status
) {

    public static CreateTransactionOutput from(Transaction transaction) {
        return new CreateTransactionOutput(
                transaction.getId().getValue(),
                WalletOutput.from(transaction.getPayee()),
                WalletOutput.from(transaction.getPayer()),
                transaction.getAmount(),
                transaction.getCreatedAt(),
                transaction.getStatus()
        );
    }
}
