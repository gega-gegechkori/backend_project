package ge.technicShop.controllers;

import ge.technicShop.services.TermsAndConditionsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/terms-and-conditions")
public class TermsAndConditionsController {
    private final TermsAndConditionsService service;

    public TermsAndConditionsController(TermsAndConditionsService service) {
        this.service = service;
    }

    @GetMapping
    public String getTermsAndConditions() {
        return service.getTermsAndConditions();
    }
}