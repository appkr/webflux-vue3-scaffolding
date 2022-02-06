package dev.appkr;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

  @GetMapping("/api/users")
  public Mono<Authentication> getUser(@AuthenticationPrincipal Mono<Authentication> authentication) {
    return authentication;
  }
}
