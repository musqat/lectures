package com.muscat.accounts;

import com.muscat.accounts.dto.AccountsContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
    info = @Info(
        title = "Accounts microservice REST API Documentation",
        description = "Bank Accounts microservice REST API Documentation",
        version = "v1",
        contact = @Contact(
            name = "Muscat Han",
            email = "muscat@example.com",
            url = "https://github.com/"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://github.com/"
        )
    ),
    externalDocs = @ExternalDocumentation(
        description = "Accounts microservice REST API Documentation",
        url = "https://github.com/swagger-ui,html"
    )
)
public class AccountsApplication {

  public static void main(String[] args) {
    SpringApplication.run(AccountsApplication.class, args);
  }

}
