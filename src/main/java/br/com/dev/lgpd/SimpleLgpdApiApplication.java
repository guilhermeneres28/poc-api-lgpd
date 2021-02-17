package br.com.dev.lgpd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(enableDefaultTransactions = false)
public class SimpleLgpdApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleLgpdApiApplication.class, args);
    }

}
