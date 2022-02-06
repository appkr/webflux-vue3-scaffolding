package dev.appkr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
@RequiredArgsConstructor
@Slf4j
public class Application {

  private final ExampleRepository repository;

  @EventListener(ApplicationReadyEvent.class)
  void initDatabase() {
    Flux<Example> names = Flux.just("foo", "bar")
        .map(name -> new Example(name))
        .flatMap(repository::save);

    repository
        .deleteAll()
        .thenMany(names)
        .thenMany(repository.findAll())
        .subscribe(e -> log.info("{}", e));
  }

  @GetMapping("/api/examples")
  public Flux<Example> listExamples() {
    return repository.findAll().log();
  }

  @PostMapping("/api/examples")
  public Mono<Example> createExample(@RequestBody Example dto) {
    return repository.save(dto);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
