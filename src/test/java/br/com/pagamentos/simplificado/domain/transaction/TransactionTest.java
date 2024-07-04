package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.shared.domain.exceptions.NotificationException;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    Wallet receiver = Wallet.create(null, null, 0.0);
    Wallet payer = Wallet.create(null, null, 0.0);

    @Test
    void createTransaction() {
        Transaction transaction = Transaction.create(receiver, payer, 100.0);
        assertNotNull(transaction);
        assertEquals("receiver", transaction.getReceiver());
        assertEquals("payer", transaction.getPayer());
        assertEquals(100.0, transaction.getAmount());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    void createWhitAValidId() {
        Transaction transaction = Transaction.create(receiver, payer, 100.0);

        assertInstanceOf(TransactionId.class, transaction.getId());
    }

    @Test
    void approveTransaction() {
        Transaction transaction = Transaction.create(receiver, payer, 100.0);
        transaction.approve();
        assertEquals(TransactionStatus.APPROVED, transaction.getStatus());
    }

    @Test
    void denyTransaction() {
        Transaction transaction = Transaction.create(receiver, payer, 100.0);
        transaction.deny();
        assertEquals(TransactionStatus.DENIED, transaction.getStatus());
    }

    @Test
    void validateTransaction() {
        Transaction transaction = Transaction.create(receiver, payer, 100.0);
        assertDoesNotThrow(transaction::validate);
    }

    @Test
    void validateTransactionValueZeroOrLower() {
        assertThrows(NotificationException.class, () -> {
            Transaction transaction = Transaction.create(receiver, payer, 0.0);
            transaction.validate();
        });

        assertThrows(NotificationException.class, () -> {
            Transaction transaction = Transaction.create(receiver, payer, -100.0);
            transaction.validate();
        });
    }
}