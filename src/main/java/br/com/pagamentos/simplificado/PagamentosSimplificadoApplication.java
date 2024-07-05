package br.com.pagamentos.simplificado;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PagamentosSimplificadoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PagamentosSimplificadoApplication.class, args);
    }

}
