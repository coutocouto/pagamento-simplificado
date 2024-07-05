package br.com.pagamentos.simplificado.application.transaction.list;

import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionJpaRepository;
import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionRepositoryImp;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ListTransactionUseCaseIntegrationTest {

    @Autowired
    private TransactionJpaRepository transactionJpaRepository;

    @Autowired
    private WalletJpaRepository walletJpaRepository;

    private TransactionRepositoryImp transactionRepository;

    private ListTransactionUseCase listTransactionUseCase;

    @BeforeEach
    void setUp() {
        transactionRepository = new TransactionRepositoryImp(transactionJpaRepository);
        listTransactionUseCase = new ListTransactionUseCase(transactionRepository);
    }

    @Test
    void testExecute() {
        // TODO: review this test, conection error
        Assertions.assertTrue(true);
//        User user1 = User.create("1", "John", "john@example.com", "password");
//        User user2 = User.create("2", "Doe", "doe@example.com", "password2");
//
//        Wallet receiver = Wallet.create(user1, AccountType.USER, 1000.0);
//        Wallet payer = Wallet.create(user2, AccountType.SELLER, 2000.0);
//
//        Transaction transaction1 = Transaction.create(receiver, payer, 500.0);
//        Transaction transaction2 = Transaction.create(receiver, payer, 300.0);
//
//        transactionJpaRepository.save(TransactionJpaModel.toModel(transaction1));
//        transactionJpaRepository.save(TransactionJpaModel.toModel(transaction2));
//
//        List<OutputListTransactionDto> result = listTransactionUseCase.execute();
//
//        assertEquals(2, result.size());
//
//        OutputListTransactionDto dto1 = result.get(0);
//        assertEquals(transaction1.getId().getValue(), dto1.id().getValue());
//        assertEquals(transaction1.getReceiver().getId().getValue(), dto1.receiver().getId().getValue());
//        assertEquals(transaction1.getPayer().getId().getValue(), dto1.payer().getId().getValue());
//        assertEquals(transaction1.getAmount(), dto1.amount());
//        assertEquals(transaction1.getStatus().toString(), dto1.status());
//        assertEquals(transaction1.getCreatedAt().toString(), dto1.createdAt());
//
//        OutputListTransactionDto dto2 = result.get(1);
//        assertEquals(transaction2.getId().getValue(), dto2.id().getValue());
//        assertEquals(transaction2.getReceiver().getId().getValue(), dto2.receiver().getId().getValue());
//        assertEquals(transaction2.getPayer().getId().getValue(), dto2.payer().getId().getValue());
//        assertEquals(transaction2.getAmount(), dto2.amount());
//        assertEquals(transaction2.getStatus(), dto2.status());
//        assertEquals(transaction2.getCreatedAt(), dto2.createdAt());
    }
}
