package dev.appkr;

import dev.appkr.api.ExampleApiDelegate;
import dev.appkr.api.model.ExampleDto;
import dev.appkr.api.model.ExampleListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ExampleApiDelegateImpl implements ExampleApiDelegate {

  private final ExampleRepository repository;

  @Override
  public Mono<ResponseEntity<ExampleDto>> createExample(Mono<ExampleDto> exampleDto, ServerWebExchange exchange) {
    return exampleDto.map(dto -> new Example(dto.getName()))                        // Mono<Example>
        .flatMap(entity -> repository.save(entity))                                 // Mono<Example>
        .map(entity -> new ExampleDto().id(entity.getId()).name(entity.getName()))  // Mono<ExampleDto>
        .map(dto -> ResponseEntity.ok(dto));                                        // Mono<ResponseEntity<ExampleDto>>
  }

  @Override
  public Mono<ResponseEntity<ExampleListDto>> listExamples(ServerWebExchange exchange) {
    final ExampleListDto listDto = new ExampleListDto();
    repository.findAll()                                                            // Flux<Example>
        .map(entity -> new ExampleDto().id(entity.getId()).name(entity.getName()))  // Flux<ExampleDto>
        .map(dto -> listDto.addDataItem(dto))                                       // Flux<ListExampleDto>
        .subscribe();

    return Mono.just(ResponseEntity.ok(listDto));
  }
}
