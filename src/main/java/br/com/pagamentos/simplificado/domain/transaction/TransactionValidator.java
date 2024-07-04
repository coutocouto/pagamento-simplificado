package br.com.pagamentos.simplificado.domain.transaction;

import br.com.pagamentos.simplificado.shared.domain.validation.Notification;

public class TransactionValidator {
    public void validate(Notification notification, Transaction transaction) {
        validatePayee(notification, transaction);
        validateReceiver(notification, transaction);
        validateValue(notification, transaction);
    }

    private void validatePayee(Notification notification, Transaction transaction) {
        // TODO: Validate payee field
    }

    private void validateReceiver(Notification notification, Transaction transaction) {
        // TODO: Validate receiver field
    }

    private void validateValue(Notification notification, Transaction transaction) {
       if(transaction.getAmount() <= 0) {
           notification.addError("The value must be greater than 0", "value");
       }
    }

}
