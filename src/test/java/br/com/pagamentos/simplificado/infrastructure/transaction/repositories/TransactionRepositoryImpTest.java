package br.com.pagamentos.simplificado.infrastructure.transaction.repositories;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.infrastructure.transaction.TransactionJpaModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionRepositoryImpTest {

    @Mock
    private TransactionJpaRepository transactionJpaRepository;

    @InjectMocks
    private TransactionRepositoryImp transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        User user1 = User.create("1", "John", "john@example.com", "password");
        User user2 = User.create("2", "Doe", "doe@example.com", "password2");

        Wallet receiver = Wallet.create(user1, AccountType.USER, 1000.0);
        Wallet payer = Wallet.create(user2, AccountType.SELLER, 2000.0);

        Transaction transaction = Transaction.create(receiver, payer, 500.0);
        TransactionJpaModel transactionJpaModel = TransactionJpaModel.toModel(transaction);

        when(transactionJpaRepository.save(any(TransactionJpaModel.class))).thenReturn(transactionJpaModel);

        Transaction savedTransaction = transactionRepository.create(transaction);

        assertEquals(transaction.getId().getValue(), savedTransaction.getId().getValue());
        verify(transactionJpaRepository, times(1)).save(any(TransactionJpaModel.class));
    }

    @Test
    void testList() {
        User user1 = User.create("1", "John", "john@example.com", "password");
        User user2 = User.create("2", "Doe", "doe@example.com", "password2");

        Wallet receiver = Wallet.create(user1, AccountType.USER, 1000.0);
        Wallet payer = Wallet.create(user2, AccountType.SELLER, 2000.0);

        Transaction transaction1 = Transaction.create(receiver, payer, 500.0);
        Transaction transaction2 = Transaction.create(receiver, payer, 300.0);

        TransactionJpaModel transactionJpaModel1 = TransactionJpaModel.toModel(transaction1);
        TransactionJpaModel transactionJpaModel2 = TransactionJpaModel.toModel(transaction2);

        when(transactionJpaRepository.findAll()).thenReturn(Arrays.asList(transactionJpaModel1, transactionJpaModel2));

        List<Transaction> transactions = transactionRepository.list();

        assertEquals(2, transactions.size());
        assertEquals(transaction1.getId().getValue(), transactions.get(0).getId().getValue());
        assertEquals(transaction2.getId().getValue(), transactions.get(1).getId().getValue());
        verify(transactionJpaRepository, times(1)).findAll();
    }
}