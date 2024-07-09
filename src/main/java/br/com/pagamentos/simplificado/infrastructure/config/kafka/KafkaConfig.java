package br.com.pagamentos.simplificado.infrastructure.config.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic notificationTopic() {
        return new NewTopic("transaction-notification", 1, (short) 1);
    }
}