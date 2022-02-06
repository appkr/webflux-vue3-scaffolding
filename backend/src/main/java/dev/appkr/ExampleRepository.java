package dev.appkr;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ExampleRepository extends R2dbcRepository<Example, Long> {
}
