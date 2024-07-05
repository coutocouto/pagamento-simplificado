package br.com.pagamentos.simplificado.application.transaction.list;

import br.com.pagamentos.simplificado.infrastructure.transaction.repository.TransactionRepositoryImp;
import br.com.pagamentos.simplificado.shared.application.ListUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTransactionUseCase extends ListUseCase<ListTransactionOutput> {

    private final TransactionRepositoryImp repository;

    public ListTransactionUseCase(TransactionRepositoryImp repository) {
        this.repository = repository;
    }

    @Override
    public List<ListTransactionOutput> execute() {
        return this.repository.list().stream()
                .map(ListTransactionOutput::from)
                .toList();
    }
}
