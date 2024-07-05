package br.com.pagamentos.simplificado.infrastructure.user.repository;

import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.domain.user.UserId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity(name = "User")
@Table(name = "users")
@Getter
public class UserJpaModel {
    @Id
    private String id;
    private String fullName;
    private String cpfCnpj;
    private String email;
    private String password;

    protected UserJpaModel() {
    }

    private UserJpaModel(String id, String fullName, String cpfCnpj, String email, String password) {
        this.id = id;
        this.fullName = fullName;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.password = password;
    }

    public static UserJpaModel create(String id, String fullName, String cpfCnpj, String email, String password) {
        return new UserJpaModel(id, fullName, cpfCnpj, email, password);
    }

    public static UserJpaModel toModel(User user) {
        return new UserJpaModel(
                user.getId().getValue(),
                user.getFullName(),
                user.getCpfCnpj(),
                user.getEmail(),
                user.getPassword()
        );
    }

    public User toEntity() {
        return User.with(
                UserId.from(this.id),
                this.fullName,
                this.cpfCnpj,
                this.email,
                this.password
        );
    }


}
