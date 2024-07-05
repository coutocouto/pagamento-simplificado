package br.com.pagamentos.simplificado.domain.user;

import br.com.pagamentos.simplificado.shared.domain.Entity;

public class User extends Entity<UserId> {
    private final String fullName;
    private final String cpfCnpj;
    private final String email;
    private final String password;

    private User(UserId id,
                 String fullName,
                 String cpfCnpj,
                 String email,
                 String password
    ) {
        super(id);
        this.fullName = fullName;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.password = password;
    }

    public static User create(
            String fullName,
            String cpfCnpj,
            String email,
            String password
    ) {
        return new User(
                UserId.generate(),
                fullName,
                cpfCnpj,
                email,
                password
        );
    }

    public static User with(
            UserId id,
            String fullName,
            String cpfCnpj,
            String email,
            String password
    ) {
        return new User(
                id,
                fullName,
                cpfCnpj,
                email,
                password
        );
    }

    public String getFullName() {
        return fullName;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void validate() {

    }
}
