package com.example.rentvideo.dto;

public class VideoResponse {
    private Long id;
    private String title;
    private String director;
    private String genre;
    private boolean available;

    public VideoResponse() {
    }

    public VideoResponse(Long id, String title, String director, String genre, boolean available) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.available = available;
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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
