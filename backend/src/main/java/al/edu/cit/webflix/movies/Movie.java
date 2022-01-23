package al.edu.cit.webflix.movies;

import al.edu.cit.webflix.movies.actors.Actor;
import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.languages.Language;
import al.edu.cit.webflix.people.Person;
import al.edu.cit.webflix.movies.scriptwriter.Scriptwriter;
import al.edu.cit.webflix.movies.trailers.Trailer;
import lombok.Data;

import java.util.List;

@Data
public class Movie {
    private int id;

    private String title;

    private int publishingYear;

    private int duration;

    private String synopsis;

    private String cover;

    private List<Actor> actors;

    private List<Trailer> trailers;

    private List<Scriptwriter> scriptwriters;

    private List<Country> productionCountries;

    private Language language;

    private Person director;

    private List<Genre> genres;
}
