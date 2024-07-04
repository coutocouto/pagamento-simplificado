package br.com.pagamentos.simplificado.infrastructure.wallet.repositories;

import br.com.pagamentos.simplificado.infrastructure.wallet.WalletJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletJpaRepository extends JpaRepository<WalletJpaModel, String> {
}
