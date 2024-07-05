package br.com.pagamentos.simplificado.application.transaction.notification;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.infrastructure.feign.notification.NotificationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationClient notificationClient;

    public NotificationConsumer(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    @KafkaListener(topics = "transaction-notification", groupId = "pagamento-simplificado")
    public void receiveNotification(Transaction transaction) {
        try {
            log.info("notifying transaction {}", transaction);

            ResponseEntity<Void> response = notificationClient.sendNotification(transaction);

            if (response.getStatusCode().isError())
                log.error("Error while sending notification, status code is not OK");

            log.info("notification has been sent {}", response.getBody());
        } catch (Exception e) {
            log.error("Error while receiving notification", e);
        }

    }
}
