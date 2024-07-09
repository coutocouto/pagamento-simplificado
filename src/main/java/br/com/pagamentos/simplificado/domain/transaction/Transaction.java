package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.shared.domain.Entity;

import java.time.Instant;

public class Transaction extends Entity<TransactionId> {
    private final Wallet payee;
    private final Wallet payer;
    private final double amount;
    private final Instant createdAt;
    private TransactionStatus status;

    private Transaction(TransactionId id, Wallet payee, Wallet payer, double amount, TransactionStatus status, Instant createdAt) {
        super(id);
        this.payee = payee;
        this.payer = payer;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Transaction create(Wallet payee, Wallet payer, double amount) {
        TransactionId id = TransactionId.generate();
        Instant now = Instant.now();
        TransactionStatus transactionStatus = TransactionStatus.PENDING;
        Transaction transaction = new Transaction(id, payee, payer, amount, transactionStatus, now);
        transaction.validate();
        return transaction;
    }


    public static Transaction with(TransactionId id, Wallet payee, Wallet payer, double amount, TransactionStatus status, Instant createdAt) {
        return new Transaction(id, payee, payer, amount, status, createdAt);
    }

    @Override
    public void validate() {
        TransactionValidator validator = new TransactionValidator();
        validator.validate(this.notification, this);
    }

    public void approve() {
        this.status = TransactionStatus.APPROVED;
    }

    public void deny() {
        this.status = TransactionStatus.DENIED;
    }

    public Wallet getPayee() {
        return payee;
    }

    public Wallet getPayer() {
        return payer;
    }

    public double getAmount() {
        return amount;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
