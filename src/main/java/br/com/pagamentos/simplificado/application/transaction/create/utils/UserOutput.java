package br.com.pagamentos.simplificado.application.transaction.create.utils;

import br.com.pagamentos.simplificado.domain.user.User;

public record UserOutput(
        String id,
        String fullName,
        String cpfCnpj,
        String email
) {

    public static UserOutput from(User user) {
        String id = user.getId().getValue();
        String fullName = user.getFullName();
        String cpfCnpj = user.getCpfCnpj();
        String email = user.getEmail();

        return new UserOutput(id, fullName, cpfCnpj, email);
    }
}
