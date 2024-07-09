package br.com.pagamentos.simplificado.infrastructure.transaction.controller;

import br.com.pagamentos.simplificado.application.transaction.create.CreateTransactionInput;
import br.com.pagamentos.simplificado.application.transaction.create.CreateTransactionOutput;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCreateTransaction() {
        CreateTransactionInput input = new CreateTransactionInput("10", "11", 100.0);

        ResponseEntity<CreateTransactionOutput> response = restTemplate.postForEntity(
                "http://localhost:" + port + "/transfer",
                input,
                CreateTransactionOutput.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        CreateTransactionOutput transactionOutput = response.getBody();
        assertEquals("10", transactionOutput.payer().id());
        assertEquals("11", transactionOutput.payee().id());
        assertEquals(100.0, transactionOutput.amount());
        assertEquals(TransactionStatus.APPROVED, transactionOutput.status());
    }

}
