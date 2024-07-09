package br.com.pagamentos.simplificado.infrastructure.transaction.controller;

import br.com.pagamentos.simplificado.application.transaction.create.CreateTransactionInput;
import br.com.pagamentos.simplificado.application.transaction.create.CreateTransactionOutput;
import br.com.pagamentos.simplificado.application.transaction.create.CreateTransactionUseCase;
import br.com.pagamentos.simplificado.application.transaction.list.ListTransactionOutput;
import br.com.pagamentos.simplificado.application.transaction.list.ListTransactionUseCase;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final ListTransactionUseCase listTransactionUseCase;
    private final CreateTransactionUseCase createTransactionUseCase;

    public TransactionController(ListTransactionUseCase listTransactionUseCase, CreateTransactionUseCase createTransactionUseCase) {
        this.listTransactionUseCase = listTransactionUseCase;
        this.createTransactionUseCase = createTransactionUseCase;
    }

    @GetMapping("/transactions")
    public List<ListTransactionOutput> listTransactions() {
        return listTransactionUseCase.execute();
    }

    @PostMapping("/transfer")
    public CreateTransactionOutput createTransaction(@RequestBody @Valid CreateTransactionInput input) {
        return createTransactionUseCase.execute(input);
    }
}
