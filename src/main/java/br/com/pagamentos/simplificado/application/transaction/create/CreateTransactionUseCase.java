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
import br.com.pagamentos.simplificado.shared.domain.exceptions.EntityNotFound;
import br.com.pagamentos.simplificado.shared.domain.exceptions.ValidationException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class CreateTransactionUseCase extends UseCase<CreateTransactionInput, CreateTransactionOutput> {

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

    @Override
    public CreateTransactionOutput execute(CreateTransactionInput createTransactionInput) {
        String payeeId = createTransactionInput.payee().toString();
        String payerId = createTransactionInput.payer().toString();
        double value = createTransactionInput.value();

        Wallet payee = walletRepository.findById(payeeId).orElseThrow(
                () -> new EntityNotFound("Payee not found")).toEntity();
        Wallet payer = walletRepository.findById(payerId).orElseThrow(
                () -> new EntityNotFound("Payer not found")).toEntity();

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

        return CreateTransactionOutput.from(savedTransaction);
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction.getNotification().hasErrors()) {
            transaction.deny();
            throw new ValidationException(transaction.getNotification().getErrors());
        }
        if (!authorizationService.isAuthorized()) {
            transaction.deny();
            throw new AuthorizationException("The transaction is not authorized");
        }
    }

}
