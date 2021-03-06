package fr.sasvb.labuvette;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import fr.sasvb.labuvette.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class LaBuvetteApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaBuvetteApplication.class, args);
    }

    @Bean
    public Algorithm jwtAlgorithm() {
        // This app does not run in production
        return Algorithm.HMAC256("my-JWT-secret");
    }

    @Bean
    public JWTVerifier verifier(Algorithm algorithm) {
        return JWT
                .require(algorithm)
                .withIssuer("la-buvette-graphql-api")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserService userService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
