package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.domain.wallet.WalletId;
import br.com.pagamentos.simplificado.shared.domain.Entity;
import br.com.pagamentos.simplificado.shared.domain.Uuid;

import java.time.Instant;

public class Transaction extends Entity<TransactionId> {
    private Wallet receiver;
    private Wallet payer;
    private double amount;
    private TransactionStatus status;
    private Instant createdAt;

    private Transaction(TransactionId id, Wallet receiver, Wallet payer, double amount, TransactionStatus status, Instant createdAt) {
        super(id);
        this.receiver = receiver;
        this.payer = payer;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Transaction create(Wallet receiver, Wallet payer, double amount) {
        TransactionId id = TransactionId.generate();
        Instant now = Instant.now();
        TransactionStatus transactionStatus = TransactionStatus.PENDING;
        Transaction transaction = new Transaction(id, receiver, payer, amount, transactionStatus, now);
        transaction.validate();
        return transaction;
    }

    @Override
    public void validate() {
        TransactionValidator validator = new TransactionValidator();
        validator.validate(this.notification, this);
        super.validate();
    }

    public static Transaction with(TransactionId id, Wallet receiver, Wallet payer, double amount, TransactionStatus status, Instant createdAt) {
        return new Transaction(id, receiver, payer, amount, status, createdAt);
    }

    public void approve() {
        this.status = TransactionStatus.APPROVED;
    }

    public void deny() {
        this.status = TransactionStatus.DENIED;
    }

    public Wallet getReceiver() {
        return receiver;
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
