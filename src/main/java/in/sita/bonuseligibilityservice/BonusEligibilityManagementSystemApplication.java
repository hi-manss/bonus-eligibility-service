package in.sita.bonuseligibilityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"in.sita.bonuseligibilityservice"})
@EntityScan(basePackages = "in.sita.bonuseligibilityservice.entity")

public class BonusEligibilityManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BonusEligibilityManagementSystemApplication.class, args);
    }

}
