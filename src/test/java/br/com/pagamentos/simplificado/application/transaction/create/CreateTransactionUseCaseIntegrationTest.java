package br.com.pagamentos.simplificado.application.transaction.create;

import br.com.pagamentos.simplificado.application.transaction.authorizer.AuthorizationService;
import br.com.pagamentos.simplificado.application.transaction.notification.NotificationService;
import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.infrastructure.exception.AuthorizationException;
import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionJpaRepository;
import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionRepositoryImp;
import br.com.pagamentos.simplificado.infrastructure.user.repository.UserJpaModel;
import br.com.pagamentos.simplificado.infrastructure.user.repository.UserJpaRepository;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaModel;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaRepository;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CreateTransactionUseCaseIntegrationTest {

    @Autowired
    private TransactionJpaRepository transactionJpaRepository;

    @Autowired
    private WalletJpaRepository walletRepository;

    @Autowired
    private UserJpaRepository userRepository;

    @MockBean
    private AuthorizationService authorizationService;

    @MockBean
    private NotificationService notificationService;

    private CreateTransactionUseCase createTransactionUseCase;

    private TransactionRepositoryImp transactionRepository;

    private Wallet payee;
    private Wallet payer;

    @BeforeEach
    void setUp() {
        User userPayee = User.create("1", "John", "john@example.com", "password");
        User userPayer = User.create("2", "Doe", "doe@example.com", "password2");
        userRepository.saveAll(List.of(UserJpaModel.toModel(userPayee), UserJpaModel.toModel(userPayer)));

        payer = Wallet.create(userPayer, AccountType.USER, 2000.0);
        payee = Wallet.create(userPayee, AccountType.SELLER, 1000.0);
        walletRepository.saveAll(List.of(WalletJpaModel.toModel(payee), WalletJpaModel.toModel(payer)));

        transactionRepository = new TransactionRepositoryImp(transactionJpaRepository);
        createTransactionUseCase = new CreateTransactionUseCase(
                transactionRepository,
                walletRepository,
                authorizationService,
                notificationService
        );
    }

    @Test
    void testCreateTransaction_Success() {
        CreateTransactionInput input = new CreateTransactionInput(payer.getId().getValue(), payee.getId().getValue(), 100.0);

        when(authorizationService.isAuthorized()).thenReturn(true);

        CreateTransactionOutput output = createTransactionUseCase.execute(input);
        System.out.println(output.id());

        // Verify
        Transaction resultTransaction = transactionRepository.findById(output.id());

        assertNotNull(resultTransaction);
        assertEquals(1900.0, resultTransaction.getPayer().getBalance());
        assertEquals(1100.0, resultTransaction.getPayee().getBalance());
        assertEquals(TransactionStatus.APPROVED, resultTransaction.getStatus());
    }

    @Test
    void testCreateTransaction_AuthorizationFails() {
        CreateTransactionInput input = new CreateTransactionInput(payer.getId().getValue(), payee.getId().getValue(), 100.0);
        when(authorizationService.isAuthorized()).thenReturn(false);

        assertThrows(AuthorizationException.class, () -> createTransactionUseCase.execute(input));
    }

    @Test
    void testCreateTransaction_ValidationException() {
        CreateTransactionInput input = new CreateTransactionInput(payer.getId().getValue(), payee.getId().getValue(), 3000.0);
        when(authorizationService.isAuthorized()).thenReturn(true);

        assertThrows(ValidationException.class, () -> createTransactionUseCase.execute(input));
    }

    @Test
    void testTransactionNotification() {
        CreateTransactionInput input = new CreateTransactionInput(payer.getId().getValue(), payee.getId().getValue(), 100.0);
        when(authorizationService.isAuthorized()).thenReturn(true);

        createTransactionUseCase.execute(input);

        verify(notificationService, times(1)).notify(any(Transaction.class));
    }
}
