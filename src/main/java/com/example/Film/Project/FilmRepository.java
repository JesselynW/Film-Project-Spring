package com.example.Film.Project;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

//    @Query("SELECT f FROM Film f WHERE f.title = ?1")
    Optional<Film> findFilmByTitle(String Title);
}
