package br.com.pagamentos.simplificado.infrastructure.transaction.repositories;

import br.com.pagamentos.simplificado.infrastructure.transaction.TransactionJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionJpaRepository extends JpaRepository<TransactionJpaModel, String>{
}
