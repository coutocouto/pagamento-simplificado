package br.com.pagamentos.simplificado.infrastructure.transaction.repositories;

import br.com.pagamentos.simplificado.domain.transaction.Transaction;
import br.com.pagamentos.simplificado.domain.transaction.TransactionRepository;
import br.com.pagamentos.simplificado.infrastructure.transaction.TransactionJpaModel;

import java.util.List;

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
}
