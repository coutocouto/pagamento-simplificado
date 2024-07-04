package br.com.pagamentos.simplificado.domain.wallet;

import br.com.pagamentos.simplificado.domain.user.User;
import br.com.pagamentos.simplificado.shared.domain.Uuid;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void createAWallet() {
        User user = User.create("John Doe", "12345678", "teste@teste", "12345678");
        Wallet wallet = Wallet.create(user, AccountType.USER, 0.0);
        assertNotNull(wallet);

    }

    @Test
    void createWalletWhitAValidUuid() {
        User user = User.create("Fulano de Tal", "12345678901", "email@protected]", "1234567");
        Wallet wallet = Wallet.create(user, AccountType.USER, 0.0);
        assertNotNull(wallet.getId().getValue());
        assertInstanceOf(WalletId.class, wallet.getId());
    }
}