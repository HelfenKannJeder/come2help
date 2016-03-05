package de.helfenkannjeder.come2help.server.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EntityScan(basePackages = "de.helfenkannjeder.come2help.server.domain")
@ComponentScan(basePackages = "de.helfenkannjeder.come2help.server")
@EnableJpaRepositories("de.helfenkannjeder.come2help.server.domain.repository")
@SpringBootApplication
@EnableJSONDoc
@EnableGlobalMethodSecurity(jsr250Enabled = true)
public class Come2HelpApplication {

    public static void main(String[] args) {
        SpringApplication.run(Come2HelpApplication.class, args);
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }
}
