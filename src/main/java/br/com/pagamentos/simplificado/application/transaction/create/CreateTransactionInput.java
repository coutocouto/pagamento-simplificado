package br.com.pagamentos.simplificado.application.transaction.create;

public record CreateTransactionInput(
        Integer payer,
        Integer payee,
        Integer value
) {
}
