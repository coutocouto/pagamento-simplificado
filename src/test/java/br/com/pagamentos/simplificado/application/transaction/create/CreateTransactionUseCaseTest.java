package br.com.pagamentos.simplificado.application.transaction.create;

import br.com.pagamentos.simplificado.application.transaction.authorizer.AuthorizationService;
import br.com.pagamentos.simplificado.application.transaction.notification.NotificationService;
import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionRepository;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.infrastructure.exception.AuthorizationException;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaModel;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaRepository;
import br.com.pagamentos.simplificado.shared.domain.exceptions.EntityNotFoundException;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ValidationException;
import br.com.pagamentos.simplificado.shared.domain.validation.Notification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateTransactionUseCaseTest {

    private TransactionRepository transactionRepository;
    private WalletJpaRepository walletRepository;
    private AuthorizationService authorizationService;
    private CreateTransactionUseCase useCase;
    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        walletRepository = mock(WalletJpaRepository.class);
        authorizationService = mock(AuthorizationService.class);
        notificationService = mock(NotificationService.class);
        useCase = new CreateTransactionUseCase(transactionRepository, walletRepository, authorizationService, notificationService);
    }

    @Test
    void testCreateTransaction_Success() {
        CreateTransactionInput input = new CreateTransactionInput("1", "2", 100);
        Wallet payee = createWallet("1", 100);
        Wallet payer = createWallet("2", 100);

        when(walletRepository.findById("1")).thenReturn(Optional.of(WalletJpaModel.toModel(payee)));
        when(walletRepository.findById("2")).thenReturn(Optional.of(WalletJpaModel.toModel(payer)));
        when(authorizationService.isAuthorized()).thenReturn(true);

        ArgumentCaptor<WalletJpaModel> walletCaptor = ArgumentCaptor.forClass(WalletJpaModel.class);

        Notification notification = mock(Notification.class);
        when(notification.hasErrors()).thenReturn(false);

        Transaction transaction = Transaction.create(payee, payer, 100);
        Transaction transactionMock = mock(Transaction.class);
        when(transactionMock.getNotification()).thenReturn(notification);
        when(transactionMock.getNotification().hasErrors()).thenReturn(false);
        when(transactionRepository.create(any())).thenReturn(transaction);

        CreateTransactionOutput output = useCase.execute(input);

        verify(walletRepository, times(2)).save(walletCaptor.capture());
        assertEquals(2, walletCaptor.getAllValues().size());

        assertNotNull(output);
    }

    @Test
    void testCreateTransaction_FailAuthorization() {
        CreateTransactionInput input = new CreateTransactionInput("1", "2", 100);
        Wallet payee = createWallet("1", 100);
        Wallet payer = createWallet("2", 100);

        when(walletRepository.findById("1")).thenReturn(Optional.of(WalletJpaModel.toModel(payee)));
        when(walletRepository.findById("2")).thenReturn(Optional.of(WalletJpaModel.toModel(payer)));
        when(authorizationService.isAuthorized()).thenReturn(false);

        assertThrows(AuthorizationException.class, () -> useCase.execute(input));
    }

    @Test
    void testCreateTransaction_FailValidation() {
        CreateTransactionInput input = new CreateTransactionInput("1", "1", 0);
        Wallet payee = createWallet("1", 100);
        Wallet payer = createWallet("1", 100);

        when(walletRepository.findById("1")).thenReturn(Optional.of(WalletJpaModel.toModel(payee)));
        when(walletRepository.findById("1")).thenReturn(Optional.of(WalletJpaModel.toModel(payer)));
        when(authorizationService.isAuthorized()).thenReturn(true);
        assertThrows(ValidationException.class, () -> useCase.execute(input));
    }


    @Test
    void testCreateTransaction_WalletNotFound() {
        CreateTransactionInput input = new CreateTransactionInput("1", "2", 100);
        when(walletRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> useCase.execute(input));
    }

    private Wallet createWallet(String index, double balance) {
        String name = "Fulano de Tal " + index;
        String cpf = "123456789" + index;
        String email = "email" + index + "@protected.com";
        String password = "1234567";

        return Wallet.create(User.create(name, cpf, email, password), AccountType.USER, balance);
    }
}


