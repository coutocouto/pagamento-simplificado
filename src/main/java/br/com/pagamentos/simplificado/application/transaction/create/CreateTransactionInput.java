package br.com.pagamentos.simplificado.application.transaction.create;

import jakarta.validation.constraints.NotNull;

public record CreateTransactionInput(
        @NotNull
        String payer,
        @NotNull
        String payee,
        //validate if notnull and if value is greater than 0 and if value is a double
        @NotNull
        double value
) {
}
