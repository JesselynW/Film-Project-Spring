package com.example.Film.Project;

import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class FilmService {

//    private final FilmRepository filmRepository;
    private final FilmRepository2 filmRepository2;

    @Autowired
    public FilmService(FilmRepository2 filmRepository2) {
        this.filmRepository2 = filmRepository2;
    }

    //CREATE -> ada validasi (salah satu attribut tidak boleh null, title nya ada yang sama)
    public Mono<ResponseEntity<Map<String, Object>>> addFilm(Film film){
        Map<String, Object> response = new HashMap<>();

        //validation for one of the attribute can't be null
        if (film.getTitle() == null ||
                film.getImage() == null ||
                film.getDuration() == null ||
                film.getTitle() == null ||
                film.getGenre() == null ||
                film.getDescription() == null){
            response.put("message", "Movie attributes can't be null");
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
        }

        return filmRepository2.findFilmByTitle(film.getTitle())
                //can't add data if already exists
                .flatMap(exists -> {
                    response.put("message", "Movie with this title already exists");
                    response.put("status", HttpStatus.CONFLICT.value());

                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(response));
                })
                //save data
                .switchIfEmpty(filmRepository2.save(film)
                            .map(savedFilm -> {
                                response.put("message", "Movie is successfully created");
                                response.put("status", HttpStatus.CREATED.value());
                                response.put("data", savedFilm);
                                return ResponseEntity.status(HttpStatus.CREATED).body(response);
                            }));
    }

    //READ all data
    public Flux<Film> getAllFilms(){
        return filmRepository2.findAll();
    }

    //READ data by id
    public Mono<Film> getFilmById(Long id){
        return filmRepository2.findById(id);
    }

    //READ data by genre
    public Flux<Film> getFilmByGenre(String genre){
        return filmRepository2.findAll()
                .filter(film -> film.getGenre().equals(genre));
    }

    //UPDATE data film
    @Transactional
    public Mono<Film> updateFilm(Long id, Film film){
        return filmRepository2.findById(id)
                .switchIfEmpty(Mono.error(new IllegalStateException("Movie with ID " + id + " does not exist")))
                .flatMap(updFilm -> {
                    if (film.getTitle() != null && !Objects.equals(film.getTitle(), updFilm.getTitle()))
                        updFilm.setTitle(film.getTitle());

                    if (film.getImage() != null && !Objects.equals(film.getImage(), updFilm.getImage()))
                        updFilm.setImage(film.getImage());

                    if (film.getDuration() != null && film.getDuration() > 30 && !Objects.equals(film.getDuration(), updFilm.getDuration()))
                        updFilm.setDuration(film.getDuration());

                    if (film.getRating() != null && film.getRating() <= 0 && !Objects.equals(film.getRating(), updFilm.getRating()))
                        updFilm.setRating(film.getRating());

                    if (film.getGenre() != null && !Objects.equals(film.getGenre(), updFilm.getGenre()))
                        updFilm.setGenre(film.getGenre());

                    if (film.getDescription() != null && !Objects.equals(film.getDescription(), updFilm.getDescription()))
                        updFilm.setDescription(film.getDescription());

                    return filmRepository2.save(updFilm);
                });
    }

    //DELETE data
    public Mono<Boolean> deleteFilm(Long id){
        return filmRepository2.existsById(id)
                .flatMap(exists -> {
                    if(exists)
                        return filmRepository2.deleteById(id).then(Mono.just(true));
                    else
                        return Mono.just(false);
                });
    }

    /*
    public FilmService(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    CREATE
    public Film addFilm(Film film){
        //if there is no similar title, it will return empty
        Optional<Film> filmOptional = filmRepository.findFilmByTitle(film.getTitle());

        if(filmOptional.isEmpty())
            return filmRepository.save(film);

        if (film.getTitle() == null || film.getImage() == null || film.getDuration() == null || film.getTitle() == null || film.getGenre() == null || film.getDescription() == null)
            return null;

        return filmOptional.orElseThrow(() -> new RuntimeException("Film already exists!"));
    }

    READ all data
    @Cacheable(value = "films")
    public List<Film> getAllFilms(){
        return filmRepository.findAll();
    }

    public Flux<Film> getAllFilms(){
        return filmRepository2.findAll();
    }

    //READ by id
    public Film getFilm(Long id){
        Optional<Film> filmOptional = filmRepository.findById(id);

        if(filmOptional.isPresent())
            return Mono.just(filmOptional.get());

        return null;
    }

    public Mono<Film> getFilm(Long id){
        Mono<Film> filmMono = filmRepository2.findById(id);

        if(filmMono.equals())
            return Mono.just(filmOptional.get());

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
     */
}
