package br.com.pagamentos.simplificado.application.transaction.notification.utils;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionId;
import br.com.pagamentos.simplificado.domain.transaction.TransactionStatus;
import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.user.UserId;
import br.com.pagamentos.simplificado.domain.wallet.AccountType;
import br.com.pagamentos.simplificado.domain.wallet.Wallet;
import br.com.pagamentos.simplificado.domain.wallet.WalletId;
import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

public class TransactionDeserializer {
    private TransactionDeserializer() {
        
    }

    private static Wallet extractWallet(JsonNode walletNode) {
        WalletId id = WalletId.from(walletNode.path("id").path("value").asText());
        User user = extractUser(walletNode.path("user"));
        AccountType accountType = AccountType.valueOf(walletNode.path("accountType").asText());
        double balance = walletNode.path("balance").asDouble();
        Instant createdAt = Instant.ofEpochMilli(walletNode.path("createdAt").asLong());

        return Wallet.with(id, user, accountType, balance, createdAt);
    }

    private static User extractUser(JsonNode userNode) {
        return User.with(
                UserId.from(userNode.path("id").path("value").asText()),
                userNode.path("fullName").asText(),
                userNode.path("cpfCnpj").asText(),
                userNode.path("email").asText(),
                userNode.path("password").asText()
        );
    }

    public static Transaction extractTransaction(JsonNode transactionNode) {
        TransactionId id = TransactionId.from(transactionNode.path("id").path("value").asText());
        Wallet payee = extractWallet(transactionNode.path("payee"));
        Wallet payer = extractWallet(transactionNode.path("payer"));
        double amount = transactionNode.path("amount").asDouble();
        TransactionStatus status = TransactionStatus.valueOf(transactionNode.path("status").asText().toUpperCase());
        Instant createdAt = Instant.ofEpochMilli(transactionNode.path("createdAt").asLong());

        return Transaction.with(id, payee, payer, amount, status, createdAt);
    }

}