package br.com.pagamentos.simplificado.application.transaction.notification;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.infrastructure.feign.notification.NotificationClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.GenericMessage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NotificationConsumerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private NotificationClient notificationClient;
    @InjectMocks
    private NotificationConsumer notificationConsumer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReceiveNotification() {
        String json = "{\"id\":{\"value\":\"1\"},\"notification\":{\"errors\":{}},\"payee\":{\"id\":{\"value\":\"11\"},\"notification\":{\"errors\":{}},\"user\":{\"id\":{\"value\":\"2\"},\"notification\":{\"errors\":{}},\"fullName\":\"Maria Oliveira\",\"cpfCnpj\":\"23456789012\",\"email\":\"maria.oliveira@example.com\",\"password\":\"senha456\"},\"accountType\":\"SELLER\",\"balance\":2600.0,\"createdAt\":1720140184.188228000},\"payer\":{\"id\":{\"value\":\"10\"},\"notification\":{\"errors\":{}},\"user\":{\"id\":{\"value\":\"1\"},\"notification\":{\"errors\":{}},\"fullName\":\"João Silva\",\"cpfCnpj\":\"12345678901\",\"email\":\"joao.silva@example.com\",\"password\":\"senha123\"},\"accountType\":\"USER\",\"balance\":1400.0,\"createdAt\":1720140184.188228000},\"amount\":100.0,\"createdAt\":1720140208.607132600,\"status\":\"APPROVED\"}";
        GenericMessage message = new GenericMessage(json);

        when(notificationClient.sendNotification(any(Transaction.class)))
                .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));

        notificationConsumer.receiveNotification(message);

        verify(notificationClient, times(1)).sendNotification(any(Transaction.class));
    }

}
