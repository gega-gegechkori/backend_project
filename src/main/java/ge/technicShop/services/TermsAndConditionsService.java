package ge.technicShop.services;

import ge.technicShop.entities.TermsAndConditions;
import ge.technicShop.repositories.TermsAndConditionsRepository;
import org.springframework.stereotype.Service;

@Service
public class TermsAndConditionsService {
    private final TermsAndConditionsRepository repository;

    public TermsAndConditionsService(TermsAndConditionsRepository repository) {
        this.repository = repository;
    }

    public String getTermsAndConditions() {
        return repository.findById(1L)
                .map(TermsAndConditions::getContent)
                .orElse("Terms and conditions are not added yet.");
    }
}