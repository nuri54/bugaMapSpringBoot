package de.hhn.se.labswp.bugamap.security;

import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static de.hhn.se.labswp.bugamap.security.auth.roles.Permission.*;
import static de.hhn.se.labswp.bugamap.security.auth.roles.Role.ADMIN;
import static de.hhn.se.labswp.bugamap.security.auth.roles.Role.MANAGER;

/**
 * This class provides configuration for Spring Security for the BugaMap application.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * Configures the security filter chain with the necessary settings for authentication and
     * authorization.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/open/**")
                .permitAll()
                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())
                .requestMatchers(HttpMethod.GET,"/api/v1/management/**").hasAnyRole(ADMIN_READ.name(), MANAGER_READ.name())
                .requestMatchers(HttpMethod.POST,"/api/v1/management/**").hasAnyRole(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                .requestMatchers(HttpMethod.PUT,"/api/v1/management/**").hasAnyRole(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE,"/api/v1/management/**").hasAnyRole(ADMIN_DELETE.name(), MANAGER_DELETE.name())

                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasRole(ADMIN_READ.name())
                .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasRole(ADMIN_CREATE.name())
                .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasRole(ADMIN_UPDATE.name())
                .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasRole(ADMIN_DELETE.name())
                .anyRequest()
                .authenticated()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.cors().configurationSource(corsConfigurationSource());
        return http.build();
    }

    /**
     * Creates a CorsConfigurationSource object with the allowed origins, methods, and headers for the
     * application.
     *
     * @return a CorsConfigurationSource object with allowed origins, methods, and headers
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                // prod/test-master/localdev
                "https://www.bugamap.de/", "https://buga23cli-git-test-master-nuri54.vercel.app/", "http://localhost:4200",
                // alex.testmaster/julian.testmaster
                "https://buga23cli-git-alextestmaster-nuri54.vercel.app/", "https://buga23cli-git-juliantestmaster-nuri54.vercel.app/",
                // max.testmaster/denis.testmaster
                "https://buga23cli-git-maxtestmaster-nuri54.vercel.app/", "https://buga23cli-git-denistestmaster-nuri54.vercel.app/",
                // nuri.testmaster/peter.testmaster
                "https://buga23cli-git-nuritestmaster-nuri54.vercel.app/", "https://buga23cli-git-petertestmaster-nuri54.vercel.app/"
                ));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(
                Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
