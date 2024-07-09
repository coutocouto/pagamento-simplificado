package br.com.pagamentos.simplificado.application.transaction.notification;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class NotificationProducerTest {

    @Mock
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @InjectMocks
    private NotificationProducer notificationProducer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendNotification() {
        User user1 = User.create("1", "John", "john@example.com", "password");
        User user2 = User.create("2", "Doe", "doe@example.com", "password2");
        
        Wallet payee = Wallet.create(user1, AccountType.USER, 0.0);
        Wallet payer = Wallet.create(user2, AccountType.SELLER, 0.0);

        Transaction transaction = Transaction.create(payee, payer, 11);
        notificationProducer.sendNotification(transaction);
        verify(kafkaTemplate).send(eq("transaction-notification"), eq(transaction));
    }
}
