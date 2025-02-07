package com.example.Film.Project;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.data.annotation.Id;  // Untuk R2DBC
import org.springframework.data.relational.core.mapping.Table;

@Entity
//@Builder
@Table (name = "film")
public class Film {
    @Id
//    @SequenceGenerator(
//            name = "film_sequence",
//            sequenceName = "film_sequence",
//            //increament at 1
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "film_sequence"
//    )
    private Long id;

    @Column(name = "title")
    private String title;
    @Column(name = "image")
    private String image;
    @Column(name = "rating")
    private int rating;
    @Column(name = "duration")
    private int duration;
    @Column(name = "genre")
    private String genre;
    @Column(name = "description")
    private String description;

    public Film(String title, String image, int rating, int duration, String genre, String description) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.duration = duration;
        this.genre = genre;
        this.description = description;
    }

    public Film(){}

    public Film(Long id, String title, String image, int rating, int duration, String genre, String description) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.duration = duration;
        this.genre = genre;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
