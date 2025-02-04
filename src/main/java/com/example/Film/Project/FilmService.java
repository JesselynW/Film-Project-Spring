package com.example.Film.Project;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public List<Film> getAllFilms(){
        return filmRepository.findAll();
    }

    //READ data
    public Film getFilm(Long id){
        Optional<Film> filmOptional = filmRepository.findById(id);
        return filmOptional.get();
    }

    //UPDATE data
    @Transactional
    public void updateFilm(Long id, String title, String image, Integer duration) {

        Film film = filmRepository.findById(id).orElseThrow(() -> new IllegalStateException("film id " + id + " does not exist"));

        if (!title.isEmpty() && !Objects.equals(film.getTitle(), title))
            film.setTitle(title);

        if (!image.isEmpty() && !Objects.equals(film.getImage(), image))
            film.setImage(image);

        if (duration > 30 && !Objects.equals(film.getDuration(), duration))
            film.setDuration(duration);
    }

    //DELETE data
    public void deleteFilm(Long id){
        if(filmRepository.existsById(id)){
            filmRepository.deleteById(id);
        }
    }
}
