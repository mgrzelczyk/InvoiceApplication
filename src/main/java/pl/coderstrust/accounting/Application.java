package pl.coderstrust.accounting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("pl.coderstrust.accounting.repositories.hibernate")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
