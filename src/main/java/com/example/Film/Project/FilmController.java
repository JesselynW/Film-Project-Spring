package com.example.Film.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@CrossOrigin("")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    //READ all data
    @GetMapping("/movies")
    public ResponseEntity<List<Film>> getAllFilms(){
        List<Film> films= filmService.getAllFilms();
        return ResponseEntity.ok(films);
    }

    //READ data
    @GetMapping("/movies/{filmId}")
    public ResponseEntity<?> getFilm(@PathVariable("filmId") Long id){
        Film film = filmService.getFilm(id);

        if(film == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID " + id + " not found");

        return ResponseEntity.ok(film);
    }

    //READ film by genre
    @GetMapping("/movies/")
    public ResponseEntity<List<Film>> getFilmByGenre(@RequestParam(required = true) String genre){
        List<Film> films = filmService.getFilmByGenre(genre);
        return ResponseEntity.ok(films);
    }

    //CREATE data
    @PostMapping("/addMovie")
    public ResponseEntity<?> addFilm(@RequestBody Film film){
        Film savedFilm = filmService.addFilm(film);

        if(savedFilm == null)
            return ResponseEntity.status(HttpStatus.CREATED).body("Movie attributes can't be null");

        return ResponseEntity.status(HttpStatus.CREATED).body(savedFilm);
    }

    //DELETE data
    @DeleteMapping("/deleteMovie/{filmId}")
    public ResponseEntity<String> deleteFilm(@PathVariable("filmId") Long id){
        Boolean deleted = filmService.deleteFilm(id);
        String response = "Movie with ID " + id + " has been deleted";

        if(deleted)
            return ResponseEntity.ok(response);
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID " + id + " not found");
    }

    //UPDATE data
    @PutMapping("/updateMovie/{filmId}")
    public ResponseEntity<?> updateFilm(@PathVariable("filmId") Long id, @RequestBody Film film){
        Film updateFilm = filmService.updateFilm(id, film);

        if(updateFilm != null)
            return ResponseEntity.ok(updateFilm);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie with ID " + id + " not found");
    }
}
