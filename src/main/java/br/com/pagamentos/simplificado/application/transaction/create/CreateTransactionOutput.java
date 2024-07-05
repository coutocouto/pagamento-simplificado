package br.com.pagamentos.simplificado.application.transaction.create;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionId;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;

import java.time.Instant;

public record CreateTransactionOutput(
        TransactionId id,
        Wallet payee,
        Wallet payer,
        double amount,
        Instant createdAt,
        TransactionStatus status
) {

    public static CreateTransactionOutput from(Transaction transaction) {
        return new CreateTransactionOutput(
                transaction.getId(),
                transaction.getPayee(),
                transaction.getPayer(),
                transaction.getAmount(),
                transaction.getCreatedAt(),
                transaction.getStatus()
        );
    }
}
