package br.com.pagamentos.simplificado.infrastructure.respository.model;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TransactionJpaModelTest {

    @Test
    void testToModel() {
        User user = User.create("1", "John" , "email", "password");
        User user2 = User.create("2", "Doe", "2", "password2");

        Wallet receiver = Wallet.create(user, AccountType.USER, 1000.0);
        Wallet payer = Wallet.create(user2, AccountType.SELLER, 2000.0);

        Transaction transaction = Transaction.create( receiver, payer, 500.0);

        TransactionJpaModel transactionJpaModel = TransactionJpaModel.toModel(transaction);

        assertEquals(transaction.getId().getValue(), transactionJpaModel.getId());
        assertEquals(transaction.getReceiver().getId().getValue(), transactionJpaModel.getReceiver().getId());
        assertEquals( transaction.getPayer().getId().getValue(), transactionJpaModel.getPayer().getId());
        assertEquals(transaction.getAmount(), transactionJpaModel.getAmount());
        assertEquals(transaction.getStatus(), transactionJpaModel.getStatus());
        assertEquals(transaction.getCreatedAt(), transactionJpaModel.getCreatedAt());
    }

    @Test
    void testToEntity() {
        UserJpaModel user = UserJpaModel.create("1","1", "John" , "email", "password");
        UserJpaModel user2 = UserJpaModel.create("1", "2", "Doe", "2", "password2");

        WalletJpaModel receiverJpaModel = WalletJpaModel.create("1", user, AccountType.USER, 1000.0);
        WalletJpaModel payerJpaModel = WalletJpaModel.create("2", user2, AccountType.SELLER, 2000.0);
        TransactionJpaModel transactionJpaModel = TransactionJpaModel.create("123e4567-e89b-12d3-a456-426614174000", receiverJpaModel, payerJpaModel, 500.0);
        System.out.println("transactionJpaModel: %s\n" + transactionJpaModel.getId());
        // Act
        Transaction transaction = transactionJpaModel.toEntity();
        System.out.printf("transaction: %s\n", transaction.getId());

        // Assert
        assertEquals(transactionJpaModel.getId(), transaction.getId().getValue());
        assertEquals(transactionJpaModel.getReceiver().getId(), transaction.getReceiver().getId().getValue());
        assertEquals(transactionJpaModel.getPayer().getId(), transaction.getPayer().getId().getValue());
        assertEquals(transactionJpaModel.getAmount(), transaction.getAmount());
        assertEquals(transactionJpaModel.getStatus(), transaction.getStatus());
        assertEquals(transactionJpaModel.getCreatedAt(), transaction.getCreatedAt());
    }
}