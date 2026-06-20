package ge.technicShop.security.services;

import ge.technicShop.dto.AuthenticationResponse;
import ge.technicShop.dto.LoginData;
import ge.technicShop.dto.RegistrationRequest;
import ge.technicShop.entities.User;
import ge.technicShop.repositories.UserRepository;
import ge.technicShop.security.config.JwtService;
import ge.technicShop.utils.GeneralUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(RegistrationRequest data) throws Exception {
        GeneralUtil.checkRequiredProperties(data, List.of("firstName", "lastName", "email", "password"));

        User user = new User();
        GeneralUtil.getCopyOf(data, user, "password");
        user.setPassword(passwordEncoder.encode(data.getPassword()));

        this.repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse login(LoginData data) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(data.getUsername(), data.getPassword())
        );
        var user = repository.findByEmail(data.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("USER_NOT_FOUND"));

        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}