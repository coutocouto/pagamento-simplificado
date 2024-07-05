package br.com.pagamentos.simplificado.infrastructure.wallet.repository;

import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.domain.wallet.WalletId;
import br.com.pagamentos.simplificado.infrastructure.user.repository.UserJpaModel;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.Instant;

@Entity(name = "Wallet")
@Table(name = "wallets")
@Getter
public class WalletJpaModel {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaModel user;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private double balance;
    private Instant createdAt;

    protected WalletJpaModel() {
    }

    private WalletJpaModel(String id, UserJpaModel user, AccountType accountType, double balance, Instant createdAt) {
        this.id = id;
        this.user = user;
        this.accountType = accountType;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public static WalletJpaModel create(String id, UserJpaModel user, AccountType accountType, double balance) {
        Instant now = Instant.now();
        return new WalletJpaModel(id, user, accountType, balance, now);
    }

    public static WalletJpaModel toModel(Wallet wallet) {
        return new WalletJpaModel(
                wallet.getId().getValue(),
                UserJpaModel.toModel(wallet.getUser()),
                wallet.getAccountType(),
                wallet.getBalance(),
                wallet.getCreatedAt()
        );
    }

    public Wallet toEntity() {
        return Wallet.with(
                WalletId.from(this.id),
                this.user.toEntity(),
                this.accountType,
                this.balance,
                this.createdAt
        );
    }

}
