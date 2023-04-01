package de.hhn.se.labswp.bugaMap.security.auth;

import de.hhn.se.labswp.bugaMap.crudRepos.UserRepository;
import de.hhn.se.labswp.bugaMap.jpa.User;
import de.hhn.se.labswp.bugaMap.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    User user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .emailadress(request.getEmail())
        .discriminator(request.getDiscriminator())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();

    repository.save(user);

    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    User user = repository.findByEmailadress(request.getEmail()).orElseThrow();

    String jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }
}
