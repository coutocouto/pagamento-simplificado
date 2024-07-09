package br.com.pagamentos.simplificado.application.transaction.authorizer;

import br.com.pagamentos.simplificado.infrastructure.feign.authorization.AuthorizationClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    private final Logger log = LoggerFactory.getLogger(AuthorizationService.class);
    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized() {
        log.info("Checking if the transaction is authorized");
        try {
            authorizationClient.isAuthorized();
            return true;
        } catch (Exception e) {
            log.error("Error while checking authorization", e);
            return false;
        }
    }

}
