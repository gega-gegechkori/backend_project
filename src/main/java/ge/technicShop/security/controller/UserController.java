package ge.technicShop.security.controller;

import ge.technicShop.dto.AuthenticationResponse;
import ge.technicShop.dto.LoginData;
import ge.technicShop.dto.RegistrationRequest;
import ge.technicShop.security.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegistrationRequest rq) throws Exception {
        return this.userService.register(rq);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginData rq) {
        return this.userService.login(rq);
    }
}