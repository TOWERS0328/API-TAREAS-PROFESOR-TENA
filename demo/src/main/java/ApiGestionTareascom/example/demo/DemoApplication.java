package ApiGestionTareascom.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ApiGestionTareascom.example")
@EnableJpaRepositories(basePackages = "ApiGestionTareascom.example.repository")
@EntityScan(basePackages = "ApiGestionTareascom.example.entity")
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}