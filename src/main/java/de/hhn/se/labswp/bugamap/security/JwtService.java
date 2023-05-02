package de.hhn.se.labswp.bugamap.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * A service class responsible for handling JSON Web Tokens (JWTs).
 */
@Service
public class JwtService {

  // Logger for logging messages
  private static Logger logger = LogManager.getLogger(JwtService.class);

  // The secret key used for signing JWTs
  // https://allkeysgenerator.com/random/security-encryption-key-generator.aspx 4096bits hex
  private static final String SECRET_KEY = "38792F423F4428472B4B6250655368566D597133743677397A244326462948404D635166546A576E5A7234753778214125442A472D4B614E645267556B58703273357638792F423F4528482B4D6251655368566D597133743677397A24432646294A404E635266556A576E5A7234753778214125442A472D4B6150645367566B59703273357638792F423F4528482B4D6251655468576D5A7134743677397A24432646294A404E635266556A586E327235753878214125442A472D4B6150645367566B597033733676397924423F4528482B4D6251655468576D5A7134743777217A25432A46294A404E635266556A586E3272357538782F413F4428472B4B6150645367566B5970337336763979244226452948404D6351655468576D5A7134743777217A25432A462D4A614E645267556A586E3272357538782F413F4428472B4B6250655368566D5970337336763979244226452948404D635166546A576E5A7234743777217A25432A462D4A614E645267556B58703273357638782F413F4428472B4B6250655368566D597133743677397A244226452948404D635166546A576E5A7234753778214125442A472D4A614E645267556B58703273357638792F423F4528482B4D6250655368566D597133743677397A24432646294A404E635266546A576E5A7234753778214125442A472D4B6150645367566B5870327335";

  /**
   * Extracts the username from the JWT token.
   *
   * @param token The JWT token
   * @return The username extracted from the token
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a claim from the JWT token.
   *
   * @param token          The JWT token
   * @param claimsResolver A function that resolves the claim
   * @return The claim extracted from the token
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the JWT token.
   *
   * @param token The JWT token
   * @return All claims extracted from the token
   */
  private Claims extractAllClaims(String token) {
    return (Claims) Jwts
        .parserBuilder()
        .setSigningKey(getSigningKey())
        .build()
        .parse(token)
        .getBody();
  }

  /**
   * Retrieves the signing key used for JWT signing.
   *
   * @return The signing key used for JWT signing
   */
  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * Generates a JWT token for the provided user details.
   *
   * @param userDetails The user details for which the token is being generated
   * @return The generated JWT token
   */
  public String generateToken(UserDetails userDetails) {
    logger.info("Token generated for user: " + userDetails.getUsername());
    return Jwts.builder()
        .setSubject(userDetails.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60)))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * Checks whether the given JWT token is expired or not.
   *
   * @param token The JWT token to check.
   * @return true if the token is expired, false otherwise.
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from the given JWT token.
   *
   * @param token The JWT token to extract the expiration date from.
   * @return The expiration date of the token.
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * Checks whether the given JWT token is valid or not.
   *
   * @param token The JWT token to check.
   * @return true if the token is valid, false otherwise.
   */
  public boolean isTokenValid(String token) {
    try {
      Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
      return true;
    } catch (JwtException e) {
      logger.error("Invalid JWT token: {}", e.getMessage());
      return false;
    }
  }

  /**
   * Validates if a JWT token is valid for the provided user details.
   *
   * @param token       The JWT token
   * @param userDetails The user details for which the token is being validated
   * @return true if the token is valid for the user, false otherwise
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }


}
