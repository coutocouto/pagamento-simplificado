package br.com.pagamentos.simplificado.application.transaction.notification;

import br.com.pagamentos.simplificado.application.transaction.notification.utils.TransactionDeserializer;
import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.infrastructure.feign.notification.NotificationClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {
    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);

    private final NotificationClient notificationClient;

    public NotificationListener(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }


    // TODO: fix this method to receive a transaction whitout parse the json
    @KafkaListener(topics = "transaction-notification", groupId = "pagamento-simplificado")
    public void receiveNotification(GenericMessage message) {
        log.info("notification receive a message {}", message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(message.getPayload().toString());
            Transaction transaction = TransactionDeserializer.extractTransaction(jsonNode);
            log.info("notification transaction {}", transaction);

            ResponseEntity<Void> response = notificationClient.sendNotification(transaction);

            if (response.getStatusCode().isError())
                log.error("Error while sending notification, status code is not OK");

            log.info("notification has been sent {}", response.getBody());
        } catch (Exception e) {
            log.error("Error while receiving notification", e);
        }
    }
}
