package br.com.pagamentos.simplificado.application.transaction.list;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionId;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;

import java.time.Instant;

public record OutputListTransactionDto(
        TransactionId id,
        Wallet receiver,
        Wallet payer,
        double amount,
        TransactionStatus status,
        Instant createdAt
) {

    public static OutputListTransactionDto from(Transaction transaction) {
        return new OutputListTransactionDto(
                transaction.getId(),
                transaction.getReceiver(),
                transaction.getPayer(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt());
    }
}