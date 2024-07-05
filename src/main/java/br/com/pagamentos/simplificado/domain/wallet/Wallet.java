package br.com.pagamentos.simplificado.domain.wallet;

import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.shared.domain.Entity;

import java.time.Instant;

public class Wallet extends Entity<WalletId> {
    private final User user;
    private final AccountType accountType;
    private double balance;
    private final Instant createdAt;

    private Wallet(WalletId id, User user, AccountType accountType, double balance, Instant createdAt) {
        super(id);
        this.user = user;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public static Wallet create(User user, AccountType accountType, double balance) {
        WalletId id = WalletId.generate();
        Instant now = Instant.now();
        return new Wallet(id, user, accountType, balance, now);
    }

    public static Wallet with(WalletId id, User user, AccountType accountType, double balance, Instant createdAt) {
        return new Wallet(id, user, accountType, balance, createdAt);
    }

    public User getUser() {
        return user;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getBalance() {
        return balance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public void validate() {

    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        this.balance -= amount;
    }
}
