package br.com.pagamentos.simplificado.domain.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void createAUser() {
        User user = User.create("Fulano de Tal", "12345678901", "email@protected]", "123456");
        assertNotNull(user);
    }

    @Test
    void createUserWhitAValidUserId() {
        User user = User.create("Fulano de Tal", "12345678901", "email@protected]", "1234567");
        assertNotNull(user.getId().getValue());
        assertInstanceOf(UserId.class, user.getId());

    }

}