package dev.appkr;

import dev.appkr.api.AuthApiDelegate;
import dev.appkr.api.model.LoginRequestDto;
import dev.appkr.api.model.RefreshRequestDto;
import dev.appkr.api.model.UaaTokenDto;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {

  @Value("${application.uaa.client-id:}")
  private String clientId;
  @Value("${application.uaa.client-secret:}")
  private String clientSecret;

  private final WebClient.Builder webClientBuilder;

  @Override
  public Mono<ResponseEntity<UaaTokenDto>> login(Mono<LoginRequestDto> loginRequestDto, ServerWebExchange exchange) {
    final Mono<MultiValueMap<String, String>> requestBody = loginRequestDto.map(dto -> {
      MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
      formData.add("username", dto.getUsername());
      formData.add("password", dto.getPassword());
      formData.add("grant_type", "password");
      formData.add("scope", "openid");
      return formData;
    });

    return doReqeust(requestBody);
  }

  @Override
  public Mono<ResponseEntity<UaaTokenDto>> refresh(Mono<RefreshRequestDto> refreshRequestDto, ServerWebExchange exchange) {
    final Mono<MultiValueMap<String, String>> requestBody = refreshRequestDto.map(refreshToken -> {
      MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
      formData.add("grant_type", "refresh_token");
      formData.add("refresh_token", refreshToken.getRefreshToken());
      return formData;
    });

    return doReqeust(requestBody);
  }

  private Mono<ResponseEntity<UaaTokenDto>> doReqeust(Mono<MultiValueMap<String, String>> formData) {
    final String in = String.format("%s:%s", clientId, clientSecret);
    final String basicAuthString = Base64.getEncoder().encodeToString(in.getBytes(StandardCharsets.UTF_8));

    return webClientBuilder.baseUrl("http://localhost:9999/oauth/token").build()
        .post()
        .header("Content-type", "application/x-www-form-urlencoded")
        .header("Authorization", "Basic " + basicAuthString)
        .accept(MediaType.APPLICATION_JSON)
        .body(formData, MultiValueMap.class)
        .retrieve()
        .bodyToMono(UaaTokenDto.class)       // Mono<UaaTokenDto>
        .map(dto -> ResponseEntity.ok(dto))  // Mono<ResponseEntity<UaaTokenDto>>
        ;
  }
}
