package br.com.pagamentos.simplificado.application.transaction.list;

import br.com.pagamentos.simplificado.infrastructure.transaction.repositories.TransactionRepositoryImp;
import br.com.pagamentos.simplificado.shared.application.ListUseCase;

import java.util.List;

public class ListTransactionUseCase extends ListUseCase<OutputListTransactionDto> {

    private final TransactionRepositoryImp repository;

    public ListTransactionUseCase(TransactionRepositoryImp repository) {
        this.repository = repository;
    }

    @Override
    public List<OutputListTransactionDto> execute() {
        return this.repository.list().stream()
                .map(OutputListTransactionDto::from)
                .toList();
    }
}
