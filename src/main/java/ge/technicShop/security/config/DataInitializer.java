package ge.technicShop.security.config;

import ge.technicShop.entities.TermsAndConditions;
import ge.technicShop.repositories.TermsAndConditionsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(TermsAndConditionsRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                String content = "Terms and Conditions\n\n" +
                        "1. Acceptance of Terms: By placing an order, you agree to these terms and conditions.\n" +
                        "2. Shipping: We deliver products within 1-3 business days.\n" +
                        "3. Returns: You may return items within 14 days if they are in original condition.\n" +
                        "4. Warranty: All products come with a manufacturer warranty against defects.\n" +
                        "5. Privacy: Your personal data is protected and used only for order processing.\n" +
                        "6. Liability: We are not responsible for any damage caused by misuse of the products.";
                repository.save(new TermsAndConditions(content));
            }
        };
    }
}