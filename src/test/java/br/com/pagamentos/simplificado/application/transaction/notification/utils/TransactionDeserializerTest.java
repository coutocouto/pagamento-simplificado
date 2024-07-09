package br.com.pagamentos.simplificado.application.transaction.notification.utils;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TransactionDeserializerTest {
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testExtractTransaction() {
        ObjectNode transactionNode = objectMapper.createObjectNode();
        transactionNode.putObject("id").put("value", "transaction-id");
        transactionNode.put("amount", 100.0);
        transactionNode.put("status", "APPROVED");
        transactionNode.put("createdAt", Instant.now().toEpochMilli());

        ObjectNode payeeNode = transactionNode.putObject("payee");
        addWalletDetails(payeeNode, "11", "Maria Oliveira", "23456789012", "maria.oliveira@example.com", "password", "SELLER", 2600.0);

        ObjectNode payerNode = transactionNode.putObject("payer");
        addWalletDetails(payerNode, "10", "João Silva", "12345678901", "joao.silva@example.com", "password", "USER", 1400.0);

        Transaction transaction = TransactionDeserializer.extractTransaction(transactionNode);

        assertNotNull(transaction);
        assertEquals("transaction-id", transaction.getId().getValue());
        assertEquals(100.0, transaction.getAmount());
        assertEquals(TransactionStatus.APPROVED, transaction.getStatus());
        assertNotNull(transaction.getPayee());
        assertNotNull(transaction.getPayer());
    }

    private void addWalletDetails(ObjectNode walletNode, String walletId, String fullName, String cpfCnpj, String email, String password, String accountType, double balance) {
        walletNode.putObject("id").put("value", walletId);
        walletNode.put("accountType", accountType);
        walletNode.put("balance", balance);
        walletNode.put("createdAt", Instant.now().toEpochMilli());

        ObjectNode userNode = walletNode.putObject("user");
        userNode.putObject("id").put("value", "2"); // Hardcoded User ID
        userNode.put("fullName", fullName);
        userNode.put("cpfCnpj", cpfCnpj);
        userNode.put("email", email);
        userNode.put("password", password);
    }
}
