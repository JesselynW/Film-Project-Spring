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

        if (film.getTitle() == null || film.getImage() == null || film.getDuration() == null || film.getTitle() == null || film.getGenre() == null || film.getDescription() == null)
            return null;

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
    public Film updateFilm(Long id, Film film) {

//        if(film == null)
//            return null;

        Film updateFilm = filmRepository.findById(id).orElseThrow(() -> new IllegalStateException("film id " + id + " does not exist"));

        if (film.getTitle() != null && !Objects.equals(film.getTitle(), updateFilm.getTitle()))
            updateFilm.setTitle(film.getTitle());

        if (film.getImage() != null && !Objects.equals(film.getImage(), updateFilm.getImage()))
            updateFilm.setImage(film.getImage());

        if (film.getDuration() != null && film.getDuration() != 0 && film.getDuration() > 30 && !Objects.equals(film.getDuration(), updateFilm.getDuration()))
            updateFilm.setDuration(film.getDuration());

        if (film.getRating() != null && film.getRating() != 0 && !Objects.equals(film.getRating(), updateFilm.getRating()))
            updateFilm.setRating(film.getRating());

        if (film.getGenre() != null && !Objects.equals(film.getGenre(), updateFilm.getGenre())) {
            updateFilm.setGenre(film.getGenre());
        }

        if (film.getDescription() != null && !Objects.equals(film.getDescription(), updateFilm.getDescription()))
            updateFilm.setDescription(film.getDescription());

        return updateFilm;
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
