package br.com.pagamentos.simplificado.application.transaction.notification;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);
    private final NotificationProducer notificationProducer;

    public NotificationService(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    public void notify(Transaction transaction) {
        log.info("notifying transaction {}...", transaction);

        notificationProducer.sendNotification(transaction);
    }
}
