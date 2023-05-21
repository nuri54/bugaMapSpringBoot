package de.hhn.se.labswp.bugamap.security.auth;

import de.hhn.se.labswp.bugamap.security.auth.Roles.Role;
import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service class for authentication-related functionalities.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    /**
     * Repository for Admin entity.
     */
    private final AdminRepository repository;

    /**
     * Password encoder to encode the password for Admin entity.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * JWT Service to generate and validate JWT tokens.
     */
    private final JwtService jwtService;

    /**
     * Authentication Manager to authenticate a user.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * Method to register a new user (Admin).
     *
     * @param request the request object containing registration details
     * @return the response object containing JWT token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        Admin admin = Admin.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .emailadress(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.TOBEACCEPTED)
                .build();

        repository.save(admin);

        String jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Method to authenticate an existing user (Admin).
     *
     * @param request the request object containing authentication details
     * @return the response object containing JWT token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Admin admin = repository.findByEmailadress(request.getEmail()).orElseThrow();

        String jwtToken = jwtService.generateToken(admin);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Method to check if a given JWT token is valid or not.
     *
     * @param request the request object containing the JWT token to be checked
     * @return true if the token is valid, false otherwise
     */
    public boolean checkToken(CheckTokenRequest request) {
        return jwtService.isTokenValid(request.getToken());
    }
}
