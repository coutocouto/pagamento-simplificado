package br.com.pagamentos.simplificado.application.transaction.create;

import br.com.pagamentos.simplificado.application.transaction.authorizer.AuthorizationService;
import br.com.pagamentos.simplificado.application.transaction.notification.NotificationService;
import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionRepository;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.infrastructure.exception.AuthorizationException;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaModel;
import br.com.pagamentos.simplificado.infrastructure.wallet.repository.WalletJpaRepository;
import br.com.pagamentos.simplificado.shared.application.UseCase;
import br.com.pagamentos.simplificado.shared.domain.exceptions.EntityNotFoundException;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ValidationException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateTransactionUseCase extends UseCase<CreateTransactionInput, CreateTransactionOutput> {
    private static final Logger log = LoggerFactory.getLogger(CreateTransactionUseCase.class);

    private final TransactionRepository transactionRepository;
    private final WalletJpaRepository walletRepository;

    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;


    public CreateTransactionUseCase(TransactionRepository transactionRepository,
                                    WalletJpaRepository walletRepository,
                                    AuthorizationService authorizationService,
                                    NotificationService notificationService) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
    }

    @Transactional
    @Override
    public CreateTransactionOutput execute(CreateTransactionInput createTransactionInput) {
        log.info("Creating transaction: {}", createTransactionInput);
        String payeeId = createTransactionInput.payee();
        String payerId = createTransactionInput.payer();
        double value = createTransactionInput.value();

        Wallet payee = walletRepository.findById(payeeId).orElseThrow(
                () -> new EntityNotFoundException("Payee not found")).toEntity();
        Wallet payer = walletRepository.findById(payerId).orElseThrow(
                () -> new EntityNotFoundException("Payer not found")).toEntity();

        Transaction transaction = Transaction.create(
                payee,
                payer,
                value
        );
        validateTransaction(transaction);
        payer.debit(value);
        payee.credit(value);

        walletRepository.save(WalletJpaModel.toModel(payer));
        walletRepository.save(WalletJpaModel.toModel(payee));

        transaction.approve();
        Transaction savedTransaction = transactionRepository.create(transaction);
        notificationService.notify(savedTransaction);

        log.info("Transaction created: {}", savedTransaction);
        return CreateTransactionOutput.from(savedTransaction);
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getNotification().hasErrors()) {
            transaction.deny();
            log.error("Transaction has errors: {}", transaction.getNotification().getErrors());
            throw new ValidationException(transaction.getNotification().getErrors());
        }
        if (!authorizationService.isAuthorized()) {
            transaction.deny();
            log.error("Transaction is not authorized");
            throw new AuthorizationException("The transaction is not authorized");
        }
    }

}
