package de.hhn.se.labswp.bugamap;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * This class serves as a Spring Boot servlet initializer for deploying the application in a servlet
 * container.
 */
public class ServletInitializer extends SpringBootServletInitializer {

  /**
   * Configures the SpringApplicationBuilder with the main class of the Spring Boot application.
   *
   * @param application a SpringApplicationBuilder object to be configured
   * @return a SpringApplicationBuilder object
   */
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(BugaMapSpringBootApplication.class);
  }
}