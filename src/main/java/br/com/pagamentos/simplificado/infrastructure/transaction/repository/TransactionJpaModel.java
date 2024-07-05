package br.com.pagamentos.simplificado.infrastructure.transaction.repository;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionId;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Table(name = "transactions")
@Entity(name = "Transaction")
@Getter
public class TransactionJpaModel {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "payee", nullable = false)
    private WalletJpaModel payee;

    @ManyToOne
    @JoinColumn(name = "payer", nullable = false)
    private WalletJpaModel payer;

    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private Instant createdAt;

    public TransactionJpaModel() {
    }

    private TransactionJpaModel(
            String id,
            WalletJpaModel payee,
            WalletJpaModel payer,
            double amount,
            TransactionStatus status,
            Instant createdAt
    ) {
        this.id = id;
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static TransactionJpaModel create(String id, WalletJpaModel receiver, WalletJpaModel payer, double amount) {
        Instant now = Instant.now();
        TransactionStatus transactionStatus = TransactionStatus.PENDING;
        return new TransactionJpaModel(id, receiver, payer, amount, transactionStatus, now);
    }

    public static TransactionJpaModel toModel(Transaction transaction) {
        return new TransactionJpaModel(
                transaction.getId().getValue(),
                WalletJpaModel.toModel(transaction.getPayee()),
                WalletJpaModel.toModel(transaction.getPayer()),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getCreatedAt()
        );
    }

    public Transaction toEntity() {
        return Transaction.with(
                TransactionId.from(this.id),
                this.payee.toEntity(),
                this.payer.toEntity(),
                this.amount,
                this.status,
                this.createdAt
        );
    }

}
