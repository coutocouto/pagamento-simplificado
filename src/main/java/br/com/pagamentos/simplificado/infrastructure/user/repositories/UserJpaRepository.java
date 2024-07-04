package br.com.pagamentos.simplificado.infrastructure.user.repositories;

import br.com.pagamentos.simplificado.infrastructure.user.UserJpaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaModel, String> {
}
