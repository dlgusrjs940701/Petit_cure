package curevengers.petit_cure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class PetitCureApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetitCureApplication.class, args);
    }

}
