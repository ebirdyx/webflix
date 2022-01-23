package al.edu.cit.webflix.movies;

import al.edu.cit.webflix.actors.Actor;
import al.edu.cit.webflix.actors.ActorDao;
import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.countries.CountryDao;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.genres.GenreDao;
import al.edu.cit.webflix.languages.Language;
import al.edu.cit.webflix.languages.LanguageDao;
import al.edu.cit.webflix.movies.countries.MovieProductionCountryDao;
import al.edu.cit.webflix.movies.genres.MovieGenreDao;
import al.edu.cit.webflix.people.Person;
import al.edu.cit.webflix.people.PersonDao;
import al.edu.cit.webflix.scriptwriter.Scriptwriter;
import al.edu.cit.webflix.scriptwriter.ScriptwriterDao;
import al.edu.cit.webflix.trailers.Trailer;
import al.edu.cit.webflix.trailers.TrailerDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@AllArgsConstructor
public class MovieRowMapper implements RowMapper<Movie> {
    private LanguageDao languageDao;
    private PersonDao personDao;
    private ActorDao actorDao;
    private TrailerDao trailerDao;
    private MovieProductionCountryDao movieProductionCountryDao;
    private MovieGenreDao movieGenreDao;
    private ScriptwriterDao scriptwriterDao;

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setPublishingYear(rs.getInt("publishing_year"));
        movie.setDuration(rs.getInt("duration"));
        movie.setSynopsis(rs.getString("synopsis"));
        movie.setCover(rs.getString("cover"));

        int languageId = rs.getInt("language_id");
        Language language = languageDao.get(languageId);
        movie.setLanguage(language);

        int directorId = rs.getInt("director_id");
        Person director = personDao.get(directorId);
        movie.setDirector(director);

        List<Actor> actors = actorDao.getMovieActors(movie.getId());
        movie.setActors(actors);

        List<Trailer> trailers = trailerDao.getMovieTrailers(movie.getId());
        movie.setTrailers(trailers);

        List<Genre> genres = movieGenreDao.getMovieGenres(movie.getId());
        movie.setGenres(genres);

        List<Country> countries = movieProductionCountryDao
                .getMovieProductionCountries(movie.getId());
        movie.setProductionCountries(countries);

        List<Scriptwriter> scriptwriters = scriptwriterDao
                .getMovieScriptWriters(movie.getId());
        movie.setScriptwriters(scriptwriters);

        return movie;
    }
}
