package de.hhn.se.labswp.bugamap.security;

import de.hhn.se.labswp.bugamap.crudrepos.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class provides the Spring Security configuration for the application. It defines beans for
 * user details service, authentication provider, and authentication manager. It also provides a
 * bean for password encoder to secure user passwords in the database.
 */
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final AdminRepository repository;

  /**
   * Returns the user details for the provided email address by querying the AdminRepository.
   *
   * @return an instance of UserDetailsService
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> repository.findByEmailadress(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Sets the user details service and password encoder to the DaoAuthenticationProvider, which is
   * responsible for authentication of users.
   *
   * @return an instance of AuthenticationProvider
   */
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

  /**
   * Returns the authentication manager used for authentication purposes in the application.
   *
   * @param config - the AuthenticationConfiguration
   * @return an instance of AuthenticationManager
   * @throws Exception if an error occurs while getting the AuthenticationManager
   */
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }

  /**
   * Returns an instance of BCryptPasswordEncoder to encode user passwords with the bcrypt
   * algorithm.
   *
   * @return an instance of PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}




