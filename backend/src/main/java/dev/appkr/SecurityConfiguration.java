package dev.appkr;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.GeneralSecurityException;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws GeneralSecurityException {
    // @formatter:off
    return http
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .authorizeExchange()
        .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .pathMatchers("/", "/index.html", "/js/**", "/css/**", "/img/**").permitAll()
        .pathMatchers("/api/login").permitAll()
        .pathMatchers("/api/**").authenticated()
        .anyExchange().authenticated()
        .and()
        .build();
    // @formatter:on
  }
}
