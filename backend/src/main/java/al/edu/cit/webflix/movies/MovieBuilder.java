package al.edu.cit.webflix.movies;

import al.edu.cit.webflix.actors.Actor;
import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.languages.Language;
import al.edu.cit.webflix.people.Person;
import al.edu.cit.webflix.scriptwriter.Scriptwriter;
import al.edu.cit.webflix.trailers.Trailer;

import java.util.List;

public class MovieBuilder {
    private Movie movie = new Movie();

    private void updateMovieIds() {
        if (movie.getId() == 0)
            return;

        if (movie.getTrailers() != null)
            movie.getTrailers()
                    .forEach(trailer -> trailer.setMovieId(movie.getId()));

        if (movie.getScriptwriters() != null)
            movie.getScriptwriters()
                    .forEach(scriptwriter -> scriptwriter.setMovieId(movie.getId()));
    }

    public MovieBuilder setId(int id) {
        movie.setId(id);
        updateMovieIds();
        return this;
    }

    public MovieBuilder setTitle(String title) {
        movie.setTitle(title);
        return this;
    }

    public MovieBuilder setPublishingYear(int publishingYear) {
        movie.setPublishingYear(publishingYear);
        return this;
    }

    public MovieBuilder setDuration(int duration) {
        movie.setDuration(duration);
        return this;
    }

    public MovieBuilder setSynopsis(String synopsys) {
        movie.setSynopsis(synopsys);
        return this;
    }

    public MovieBuilder setCover(String cover) {
        movie.setCover(cover);
        return this;
    }

    public MovieBuilder setActors(List<Actor> actors) {
        movie.setActors(actors);
        return this;
    }

    public MovieBuilder setTrailers(List<Trailer> trailers) {
        movie.setTrailers(trailers);
        updateMovieIds();
        return this;
    }

    public MovieBuilder setScriptwriters(List<Scriptwriter> scriptwriters) {
        movie.setScriptwriters(scriptwriters);
        updateMovieIds();
        return this;
    }

    public MovieBuilder setProductionCountries(List<Country> productionCountries) {
        movie.setProductionCountries(productionCountries);
        return this;
    }

    public MovieBuilder setLanguage(Language language) {
        movie.setLanguage(language);
        return this;
    }

    public MovieBuilder setDirector(Person director) {
        movie.setDirector(director);
        return this;
    }

    public MovieBuilder setGenres(List<Genre> genres) {
        movie.setGenres(genres);
        return this;
    }

    public Movie build() {
        return movie;
    }
}
