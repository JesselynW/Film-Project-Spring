package com.example.Film.Project;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmRepository2 extends R2dbcRepository<Film, Long> {
    Mono<Film> findFilmByTitle(String Title);
}
