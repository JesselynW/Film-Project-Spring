package com.example.Film.Project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

        Map<String, Object> response = new HashMap<>();
        if(film == null){
            response.put("message", "Movie with ID " + id + " not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        else{
            response.put("message", "get movie with ID " + id + " success");
            response.put("status", HttpStatus.OK.value());
            response.put("data", film);
        }

        return ResponseEntity.ok(response);
    }

    //READ film by genre
    @GetMapping("/movies/")
    public ResponseEntity<Map<String, Object>> getFilmByGenre(@RequestParam(required = true) String genre){
        List<Film> films = filmService.getFilmByGenre(genre);

        Map<String, Object> response = new HashMap<>();

        if(films.size() > 0){
            response.put("message", "get movie with genre " + genre + " success");
            response.put("status", HttpStatus.OK.value());
            response.put("data", films);
            return ResponseEntity.ok(response);
        }

        response.put("message", "get movie with genre " + genre + " is not found");
        response.put("status", HttpStatus.NOT_FOUND.value());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    //CREATE data
    @PostMapping("/addMovie")
    public ResponseEntity<?> addFilm(@RequestBody Film film){
        Film savedFilm = filmService.addFilm(film);

        Map<String, Object> response = new HashMap<>();
        if(savedFilm == null){
            response.put("message", "Movie attributes can't be null");
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        else{
            response.put("message", "Movie is successfully created");
            response.put("status", HttpStatus.OK.value());
            response.put("data", savedFilm);
        }

        return ResponseEntity.ok(response);
    }

    //DELETE data
    @DeleteMapping("/deleteMovie/{filmId}")
    public ResponseEntity<Map<String, Object>> deleteFilm(@PathVariable("filmId") Long id){
        Boolean deleted = filmService.deleteFilm(id);

        Map<String, Object> response = new HashMap<>();
        if(!deleted){
            response.put("message", "Movie with ID " + id + " not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        else{
            response.put("message", "Movie with ID " + id + " has been deleted");
            response.put("status", HttpStatus.OK.value());
        }

        return ResponseEntity.ok(response);
    }

    //UPDATE data
    @PutMapping("/updateMovie/{filmId}")
    public ResponseEntity<?> updateFilm(@PathVariable("filmId") Long id, @RequestBody Film film){
        Film updateFilm = filmService.updateFilm(id, film);

        Map<String, Object> response = new HashMap<>();
        if(updateFilm == null){
            response.put("message", "Movie with ID " + id + " not found");
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        else{
            response.put("message", "Movie is successfully created");
            response.put("status", HttpStatus.OK.value());
            response.put("data", updateFilm);
        }

        return ResponseEntity.ok(response);
    }
}
