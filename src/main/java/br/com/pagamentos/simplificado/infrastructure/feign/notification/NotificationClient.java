package br.com.pagamentos.simplificado.infrastructure.feign.notification;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "NotificationClient",
        url = "https://util.devi.tools/api/v1/notify"
)
public interface NotificationClient {

    @PostMapping
    ResponseEntity<Void> sendNotification(@RequestBody Transaction transaction);
}
