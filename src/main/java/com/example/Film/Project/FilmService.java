package com.example.Film.Project;

import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FilmService {

    private final FilmRepository filmRepository;

    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    //CREATE
    public Film addFilm(Film film){
        //if there is no similar title, it will return empty
        Optional<Film> filmOptional = filmRepository.findFilmByTitle(film.getTitle());

        if(filmOptional.isEmpty())
            return filmRepository.save(film);

        return filmOptional.orElseThrow(() -> new RuntimeException("Film already exists!"));
    }

    //READ all data
//    @Cacheable(value = "films")
    public List<Film> getAllFilms(){
        return filmRepository.findAll();
    }

    //READ by id
    public Film getFilm(Long id){
        Optional<Film> filmOptional = filmRepository.findById(id);

        if(filmOptional.isPresent())
            return filmOptional.get();

        return null;
    }

    //READ by genre
    public List<Film> getFilmByGenre(String genre){
        if (genre.isEmpty())
            System.out.println("Please input genre");

        Optional<List<Film>> optionalFilms = filmRepository.findFilmByGenre(genre);

        return optionalFilms.orElse(Collections.emptyList());
    }

    //UPDATE data
    @Transactional
    public Film updateFilm(Long id, String title, String image, Integer duration, String genre, String description) {

        Film film = filmRepository.findById(id).orElseThrow(() -> new IllegalStateException("film id " + id + " does not exist"));

        if (title != null&& !Objects.equals(film.getTitle(), title))
            film.setTitle(title);

        if (image != null && !Objects.equals(film.getImage(), image))
            film.setImage(image);

        if (duration != null && duration > 30 && !Objects.equals(film.getDuration(), duration))
            film.setDuration(duration);

        if (genre != null && !Objects.equals(film.getGenre(), genre))
            film.setGenre(genre);

        if (description != null && !Objects.equals(film.getDescription(), description))
            film.setDescription(description);

        return film;
    }

    //DELETE data
//    @Caching(evict = {@CacheEvict(value = "films", allentries = true), @CacheEvict(value = "films", key = "#id")})
    public Boolean deleteFilm(Long id){
        if(filmRepository.existsById(id)){
            filmRepository.deleteById(id);
            return true;
        }

        return filmRepository.existsById(id);
    }
}
