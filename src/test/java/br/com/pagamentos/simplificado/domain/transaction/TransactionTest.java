package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ValidationException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    Wallet payee = Wallet.create(null, null, 0.0);
    Wallet payer = Wallet.create(null, null, 0.0);

    @Test
    void createTransaction() {
        Transaction transaction = Transaction.create(payee, payer, 100.0);
        assertNotNull(transaction);
        assertEquals(payee, transaction.getPayee());
        assertEquals(payer, transaction.getPayer());
        assertEquals(100.0, transaction.getAmount());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    void createWhitAValidId() {
        Transaction transaction = Transaction.create(payee, payer, 100.0);

        assertInstanceOf(TransactionId.class, transaction.getId());
    }

    @Test
    void approveTransaction() {
        Transaction transaction = Transaction.create(payee, payer, 100.0);
        transaction.approve();
        assertEquals(TransactionStatus.APPROVED, transaction.getStatus());
    }

    @Test
    void denyTransaction() {
        Transaction transaction = Transaction.create(payee, payer, 100.0);
        transaction.deny();
        assertEquals(TransactionStatus.DENIED, transaction.getStatus());
    }

    @Test
    void validateTransaction() {
        Transaction transaction = Transaction.create(payee, payer, 100.0);
        assertDoesNotThrow(transaction::validate);
    }

    @Test
    void validateTransactionValueZeroOrLower() {
        Transaction transaction = Transaction.create(payee, payer, 0.0);
        assertTrue(transaction.getNotification().hasErrors());
        assertEquals("The value must be greater than 0", transaction.getNotification().getErrors().get("value").get(0));
    }

    @Test
    void validateTransactionReceiverEqualsPayer() {
        Wallet payer = payee;
        Transaction transaction = Transaction.create(payee, payer, 100);
        assertTrue(transaction.getNotification().hasErrors());
        System.out.println(transaction.getNotification().getErrors());
        assertEquals("The payer and payee must be different", transaction.getNotification().getErrors().get("payee").get(0));
    }

    @Test
    void validateTransactionPayerIsSeller() {
        Wallet payer = Wallet.create(null, AccountType.SELLER, 0.0);
        Transaction transaction = Transaction.create(payee, payer, 100);
        assertTrue(transaction.getNotification().hasErrors());
        assertEquals("The payer cannot be a seller", transaction.getNotification().getErrors().get("payer").get(0));
    }

    @Test
    void validateTransactionPayerDoesNotHaveEnoughBalance() {
        Wallet payer = Wallet.create(null, AccountType.USER, 50.0);
        Transaction transaction = Transaction.create(payee, payer, 100);
        assertTrue(transaction.getNotification().hasErrors());
        assertEquals("The payer does not have enough balance", transaction.getNotification().getErrors().get("payer").get(0));
    }

    @Test
    void validateTransactionErrorMessages() {
        Wallet wallet1 = Wallet.create(null, AccountType.USER, 500.0);
        Wallet wallet2 = Wallet.create(null, AccountType.USER, 500.0);

        Transaction sameWalletTransaction = Transaction.create(wallet1, wallet1, 0.0);
        Transaction negativeValueTransaction = Transaction.create(wallet1, wallet2, -100.0);
        Transaction zeroValueTransaction = Transaction.create(wallet1, wallet2, 0.0);

        try {
            sameWalletTransaction.validate();
        } catch (ValidationException e) {
            assertEquals("The payer and payee must be different", e.getErrors().get("payer").get(0));
            assertEquals("The value must be greater than 0", e.getErrors().get("value").get(0));
        }

        try {
            negativeValueTransaction.validate();
        } catch (ValidationException e) {
            assertEquals("The value must be greater than 0", e.getErrors().get("value").get(0));
        }

        try {
            zeroValueTransaction.validate();
        } catch (ValidationException e) {
            assertEquals("The value must be greater than 0", e.getErrors().get("value").get(0));
        }
    }
}