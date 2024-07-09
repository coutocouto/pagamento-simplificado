package br.com.pagamentos.simplificado.application.transaction.list;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionJpaModel;
import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionJpaRepository;
import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionRepositoryImp;
import br.com.pagamentos.simplificado.infrastructure.user.repository.UserJpaModel;
import br.com.pagamentos.simplificado.infrastructure.user.repository.UserJpaRepository;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaModel;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ListTransactionUseCaseIntegrationTest {

    @Autowired
    private TransactionJpaRepository transactionJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

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
    void testListTransactions() {
        User user1 = User.create("1", "John", "john@example.com", "password");
        User user2 = User.create("2", "Doe", "doe@example.com", "password2");
        userJpaRepository.save(UserJpaModel.toModel(user1));
        userJpaRepository.save(UserJpaModel.toModel(user2));

        Wallet receiver = Wallet.create(user1, AccountType.USER, 1000.0);
        Wallet payer = Wallet.create(user2, AccountType.SELLER, 2000.0);
        walletJpaRepository.save(WalletJpaModel.toModel(receiver));
        walletJpaRepository.save(WalletJpaModel.toModel(payer));

        Transaction transaction1 = Transaction.create(receiver, payer, 500.0);
        Transaction transaction2 = Transaction.create(receiver, payer, 300.0);

        transactionJpaRepository.save(TransactionJpaModel.toModel(transaction1));
        transactionJpaRepository.save(TransactionJpaModel.toModel(transaction2));

        List<ListTransactionOutput> result = listTransactionUseCase.execute();

        assertEquals(2, result.size());
        ListTransactionOutput dto1 = result.get(0);
        assertEquals(transaction1.getId().getValue(), dto1.id().getValue());
        assertEquals(transaction1.getPayee().getId().getValue(), dto1.receiver().getId().getValue());
        assertEquals(transaction1.getPayer().getId().getValue(), dto1.payer().getId().getValue());
        assertEquals(transaction1.getAmount(), dto1.amount());
        assertEquals(transaction1.getStatus(), dto1.status());
        assertEquals(transaction1.getCreatedAt(), dto1.createdAt());

        ListTransactionOutput dto2 = result.get(1);
        assertEquals(transaction2.getId().getValue(), dto2.id().getValue());
        assertEquals(transaction2.getPayee().getId().getValue(), dto2.receiver().getId().getValue());
        assertEquals(transaction2.getPayer().getId().getValue(), dto2.payer().getId().getValue());
        assertEquals(transaction2.getAmount(), dto2.amount());
        assertEquals(transaction2.getStatus(), dto2.status());
        assertEquals(transaction2.getCreatedAt(), dto2.createdAt());
    }
}
