package br.com.pagamentos.simplificado.application.transaction.authorizer;

import br.com.pagamentos.simplificado.infrastructure.feign.authorization.AuthorizationClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthorizationServiceTest {
    private AuthorizationClient authorizationClient;
    private AuthorizationService authorizationService;

    @BeforeEach
    void setUp() {
        authorizationClient = mock(AuthorizationClient.class);
        authorizationService = new AuthorizationService(authorizationClient);
    }

    @Test
    void testIsAuthorizedWhenAuthorized() {
        when(authorizationClient.isAuthorized()).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        boolean result = authorizationService.isAuthorized();

        assertTrue(result, "The transaction should be authorized when HTTP status is 2xx");
    }
}
