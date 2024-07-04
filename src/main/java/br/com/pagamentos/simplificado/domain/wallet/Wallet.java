package br.com.pagamentos.simplificado.domain.wallet;

import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.shared.domain.Entity;
import br.com.pagamentos.simplificado.shared.domain.Uuid;

import java.time.Instant;

public class Wallet extends Entity<Uuid> {
    private User user;
    private AccountType accountType;
    private double balance;
    private Instant createdAt;

    private Wallet(Uuid id, User user, AccountType accountType, double balance, Instant createdAt) {
        super(id);
        this.user = user;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public static Wallet create(User user, AccountType accountType, double balance) {
        Uuid id = WalletId.generate();
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
}
