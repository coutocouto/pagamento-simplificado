package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.shared.domain.validation.Notification;

public class TransactionValidator {
    public void validate(Notification notification, Transaction transaction) {
        validateEqualsWallets(notification, transaction);
        validatePayer(notification, transaction);
        validateValue(notification, transaction);
    }

    private void validatePayer(Notification notification, Transaction transaction) {
        if (transaction.getPayer().getAccountType().equals(AccountType.SELLER)) {
            notification.addError("The payer cannot be a seller", "payer");
        }
        if (transaction.getPayer().getBalance() < transaction.getAmount()) {
            notification.addError("The payer does not have enough balance", "payer");
        }
    }

    private void validateValue(Notification notification, Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            notification.addError("The value must be greater than 0", "value");
        }
    }

    private void validateEqualsWallets(Notification notification, Transaction transaction) {
        if (transaction.getPayer().getId().getValue() == transaction.getPayee().getId().getValue()) {
            notification.addError("The payer and payee must be different", "payee");
        }
    }
}
