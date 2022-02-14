package dev.appkr;

import java.security.GeneralSecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.OAuth2ResourceServerSpec;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.zalando.problem.spring.webflux.advice.security.SecurityProblemSupport;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class SecurityConfiguration {

  private final SecurityProblemSupport problemSupport;

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws GeneralSecurityException {
    // @formatter:off
    return http
        .cors()
        .and()
        .csrf(spec -> spec.disable())
        .headers(spec -> spec.frameOptions().disable())
        .authorizeExchange(spec -> spec.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            .pathMatchers("/actuator/health", "/actuator/info").permitAll()
            .pathMatchers("/actuator/**").authenticated()
            .pathMatchers("/", "/index.html", "/favicon.ico", "/js/**", "/css/**", "/img/**").permitAll()
            .pathMatchers("/api/login", "/api/refresh").permitAll()
            .pathMatchers("/api/**").authenticated()
            .anyExchange().authenticated())
        .oauth2ResourceServer(OAuth2ResourceServerSpec::jwt)
        .exceptionHandling(spec -> spec.authenticationEntryPoint(problemSupport)
            .accessDeniedHandler(problemSupport))
        .build();
    // @formatter:on
  }

  @Bean
  public CorsWebFilter corsWebFilter() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.setMaxAge(3600L);
    corsConfig.addAllowedMethod("*");
    corsConfig.addAllowedHeader("*");
    corsConfig.addAllowedOriginPattern("*");

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);

    return new CorsWebFilter(source);
  }

  @Bean
  @Profile("test")
  public MapReactiveUserDetailsService users() {
    UserDetails user = User.builder()
        .username("user")
        .password(passwordEncoder().encode("pass"))
        .roles("USER")
        .build();

    return new MapReactiveUserDetailsService(user);
  }

  @Bean
  @Profile("test")
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
