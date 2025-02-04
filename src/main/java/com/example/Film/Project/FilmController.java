package com.example.Film.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    //READ all data
    @GetMapping("/movies")
    public List<Film> getAllFilms(){
        return filmService.getAllFilms();
    }

    //READ data
    @GetMapping("/movies/{filmId}")
    public Film getFilm(@PathVariable("filmId") Long id){
        return filmService.getFilm(id);
    }

    //CREATE data
    @PostMapping("/addMovie")
    public ResponseEntity<Film> addFilm(@RequestBody Film film){
        Film savedFilm = filmService.addFilm(film);
        return new ResponseEntity<>(savedFilm, HttpStatus.CREATED);
    }

    //DELETE data
    @DeleteMapping("/deleteMovie/{filmId}")
    public void deleteFilm(@PathVariable("filmId") Long id){
        filmService.deleteFilm(id);
    }

        //UPDATE data
        @PutMapping("/{filmId}")
        public void updateFilm(@PathVariable("filmId") Long id, @RequestParam(required = false) String title, @RequestParam(required = false) String image, @RequestParam(required = false) int duration){
            filmService.updateFilm(id, title, image, duration);
        }
}
