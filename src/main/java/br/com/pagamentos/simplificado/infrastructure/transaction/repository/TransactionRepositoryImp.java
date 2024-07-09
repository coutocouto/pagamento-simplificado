package br.com.pagamentos.simplificado.infrastructure.transaction.repository;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepositoryImp implements TransactionRepository {

    private final TransactionJpaRepository repository;

    public TransactionRepositoryImp(final TransactionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transaction create(Transaction entity) {
        return this.repository.save(TransactionJpaModel.toModel(entity)).toEntity();
    }

    @Override
    public List<Transaction> list() {
        return this.repository.findAll().stream().map(TransactionJpaModel::toEntity).toList();
    }

    @Override
    public Transaction findById(String id) {
        return this.repository.findById(id).map(TransactionJpaModel::toEntity).orElse(null);
    }
}
