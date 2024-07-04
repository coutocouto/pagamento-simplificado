package br.com.pagamentos.simplificado.application.transaction.list;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.infrastructure.transaction.repositories.TransactionRepositoryImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ListTransactionUseCaseTest {
    @Mock
    private TransactionRepositoryImp transactionRepository;

    @InjectMocks
    private ListTransactionUseCase listTransactionUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecute() {
        User user1 = User.create("1", "John", "john@example.com", "password");
        User user2 = User.create("2", "Doe", "doe@example.com", "password2");

        Wallet receiver = Wallet.create(user1, AccountType.USER, 1000.0);
        Wallet payer = Wallet.create(user2, AccountType.SELLER, 2000.0);

        Transaction transaction1 = Transaction.create(receiver, payer, 500.0);
        Transaction transaction2 = Transaction.create(receiver, payer, 300.0);

        when(transactionRepository.list()).thenReturn(Arrays.asList(transaction1, transaction2));

        List<OutputListTransactionDto> result = listTransactionUseCase.execute();

        assertEquals(2, result.size());

        OutputListTransactionDto dto1 = result.get(0);
        assertEquals(transaction1.getReceiver().getId().getValue(), dto1.receiver().getId().getValue());
        assertEquals(transaction1.getPayer().getId().getValue(), dto1.payer().getId().getValue());
        assertEquals(transaction1.getAmount(), dto1.amount());
        assertEquals(transaction1.getStatus(), dto1.status());
        assertEquals(transaction1.getCreatedAt(), dto1.createdAt());

        OutputListTransactionDto dto2 = result.get(1);
        assertEquals(transaction2.getReceiver().getId().getValue(), dto2.receiver().getId().getValue());
        assertEquals(transaction2.getPayer().getId().getValue(), dto2.payer().getId().getValue());
        assertEquals(transaction2.getAmount(), dto2.amount());
        assertEquals(transaction2.getStatus(), dto2.status());
        assertEquals(transaction2.getCreatedAt(), dto2.createdAt());
    }
}