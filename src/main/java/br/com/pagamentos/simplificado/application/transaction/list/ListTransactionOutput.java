package br.com.pagamentos.simplificado.application.transaction.list;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionId;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;

import java.time.Instant;

public record ListTransactionOutput(
        TransactionId id,
        Wallet receiver,
        Wallet payer,
        double amount,
        TransactionStatus status,
        Instant createdAt
) {

    public static ListTransactionOutput from(Transaction transaction) {
        return new ListTransactionOutput(
                transaction.getId(),
                transaction.getPayee(),
                transaction.getPayer(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt());
    }
}