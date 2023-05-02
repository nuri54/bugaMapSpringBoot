package de.hhn.se.labswp.bugamap.security.auth;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import de.hhn.se.labswp.bugamap.jpa.Admin;
import de.hhn.se.labswp.bugamap.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final AdminRepository repository;
  private final PasswordEncoder passwordEncoder;

  private final JwtService jwtService;

  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {
    Admin admin = Admin.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .emailadress(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();

    repository.save(admin);

    String jwtToken = jwtService.generateToken(admin);
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
    Admin admin = repository.findByEmailadress(request.getEmail()).orElseThrow();

    String jwtToken = jwtService.generateToken(admin);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public boolean checkToken(CheckTokenRequest request){
    return jwtService.isTokenValid(request.getToken());
  }
}
