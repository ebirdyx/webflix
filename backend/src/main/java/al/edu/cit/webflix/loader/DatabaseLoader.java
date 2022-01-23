package al.edu.cit.webflix.loader;

import al.edu.cit.webflix.actors.Actor;
import al.edu.cit.webflix.actors.ActorBuilder;
import al.edu.cit.webflix.actors.ActorDao;
import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.countries.CountryBuilder;
import al.edu.cit.webflix.countries.CountryDao;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.genres.GenreBuilder;
import al.edu.cit.webflix.genres.GenreDao;
import al.edu.cit.webflix.languages.Language;
import al.edu.cit.webflix.languages.LanguageBuilder;
import al.edu.cit.webflix.languages.LanguageDao;
import al.edu.cit.webflix.loader.models.movies.XMLMovies;
import al.edu.cit.webflix.loader.models.people.XMLPeople;
import al.edu.cit.webflix.movies.Movie;
import al.edu.cit.webflix.movies.MovieBuilder;
import al.edu.cit.webflix.movies.MovieDao;
import al.edu.cit.webflix.movies.countries.MovieProductionCountry;
import al.edu.cit.webflix.movies.countries.MovieProductionCountryBuilder;
import al.edu.cit.webflix.movies.countries.MovieProductionCountryDao;
import al.edu.cit.webflix.movies.genres.MovieGenre;
import al.edu.cit.webflix.movies.genres.MovieGenreBuilder;
import al.edu.cit.webflix.movies.genres.MovieGenreDao;
import al.edu.cit.webflix.people.Person;
import al.edu.cit.webflix.people.PersonBuilder;
import al.edu.cit.webflix.people.PersonDao;
import al.edu.cit.webflix.scriptwriter.Scriptwriter;
import al.edu.cit.webflix.scriptwriter.ScriptwriterBuilder;
import al.edu.cit.webflix.scriptwriter.ScriptwriterDao;
import al.edu.cit.webflix.trailers.Trailer;
import al.edu.cit.webflix.trailers.TrailerBuilder;
import al.edu.cit.webflix.trailers.TrailerDao;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static al.edu.cit.webflix.common.Utils.deserializeXmlObject;
import static al.edu.cit.webflix.common.Utils.readFileFromResources;

@Component
@AllArgsConstructor
class DatabaseLoader implements CommandLineRunner {

    private PersonDao personDao;
    private GenreDao genreDao;
    private LanguageDao languageDao;
    private CountryDao countryDao;
    private MovieDao movieDao;
    private ScriptwriterDao scriptwriterDao;
    private TrailerDao trailerDao;
    private ActorDao actorDao;
    private MovieGenreDao movieGenreDao;
    private MovieProductionCountryDao movieProductionCountryDao;

    public void loadPeople() throws IOException {
        String xml = readFileFromResources("data/people_latin1.xml");
        XMLPeople xmlPeople = (XMLPeople) deserializeXmlObject(xml, XMLPeople.class);
        List<Person> people = xmlPeople.people.stream().map(person ->
                        new PersonBuilder()
                                .setId(person.id)
                                .setName(person.name)
                                .setDob(person.birth.getDob())
                                .setBio(person.bio)
                                .setPhoto(person.photo)
                                .setBirthCity(person.birth.getCityOfBirth())
                                .setBirthState(person.birth.getStateOfBirth())
                                .setBirthCountry(person.birth.getCountryOfBirth())
                                .build())
                .collect(Collectors.toList());
        personDao.batchInsert(people);
    }

    public void loadGenres(XMLMovies xmlMovies) {
        Set<String> genresSet = new HashSet<>();

        xmlMovies.movies
                .forEach(xmlMovie -> genresSet.addAll(xmlMovie.genres));

        List<Genre> genres = genresSet.stream()
                .map(genre -> new GenreBuilder().setName(genre).build())
                .collect(Collectors.toList());

        genreDao.batchInsert(genres);
    }

    public void loadCountries(XMLMovies xmlMovies) {
        Set<String> countriesSet = new HashSet<>();

        xmlMovies.movies
                .forEach(xmlMovie -> countriesSet.addAll(xmlMovie.countries));

        List<Country> countries = countriesSet.stream()
                .map(country -> new CountryBuilder().setName(country).build())
                .collect(Collectors.toList());

        countryDao.batchInsert(countries);
    }

    public void loadLanguages(XMLMovies xmlMovies) {
        Set<String> languagesSet = new HashSet<>();

        xmlMovies.movies
                .forEach(xmlMovie -> {
                    if (xmlMovie.language == null) return;
                    languagesSet.add(xmlMovie.language);
                });

        List<Language> languages = languagesSet.stream()
                .map(language -> new LanguageBuilder().setName(language).build())
                .collect(Collectors.toList());

        languageDao.batchInsert(languages);
    }

    public void loadScriptWriters(List<Scriptwriter> scriptwriters) {
        scriptwriterDao.batchInsert(scriptwriters);
    }

    public void loadTrailers(List<Trailer> trailers) {
        trailerDao.batchInsert(trailers);
    }

    public void loadActors(List<Actor> actors) {
        actorDao.batchInsert(actors);
    }

    public void loadMovieGenres(List<MovieGenre> movieGenres) {
        movieGenreDao.batchInsert(movieGenres);
    }

    public void loadMovieProductionCountries(List<MovieProductionCountry> movieProductionCountries) {
        movieProductionCountryDao.batchInsert(movieProductionCountries);
    }

    public void loadMovies() throws IOException {
        String xml = readFileFromResources("data/movies_latin1.xml");
        XMLMovies xmlMovies = (XMLMovies) deserializeXmlObject(xml, XMLMovies.class);

        if (genreDao.count() == 0)
            loadGenres(xmlMovies);

        if (countryDao.count() == 0)
            loadCountries(xmlMovies);

        if (languageDao.count() == 0)
            loadLanguages(xmlMovies);

        List<Scriptwriter> scriptwriters = new ArrayList<>();
        List<Trailer> trailers = new ArrayList<>();
        List<Actor> actors = new ArrayList<>();
        List<MovieGenre> movieGenres = new ArrayList<>();
        List<MovieProductionCountry> movieProductionCountries = new ArrayList<>();

        List<Movie> movies = xmlMovies.movies.stream()
                .map(xmlMovie -> {
                    scriptwriters.addAll(xmlMovie.scriptwriters.stream()
                            .map(s -> new ScriptwriterBuilder()
                                    .setName(s)
                                    .setMovieId(xmlMovie.id)
                                    .build())
                            .collect(Collectors.toList()));

                    trailers.addAll(xmlMovie.trailers.stream()
                            .map(t -> new TrailerBuilder()
                                    .setLink(t)
                                    .setMovieId(xmlMovie.id)
                                    .build())
                            .collect(Collectors.toList()));

                    actors.addAll(xmlMovie.roles.stream()
                            .map(a -> new ActorBuilder()
                                    .setCharacterName(a.character)
                                    .setPerson(personDao.get(a.actor.id))
                                    .setMovieId(xmlMovie.id)
                                    .build())
                            .collect(Collectors.toList()));

                    movieGenres.addAll(xmlMovie.genres.stream()
                            .map(g -> new MovieGenreBuilder()
                                    .setGenre(genreDao.findByName(g))
                                    .setMovieId(xmlMovie.id)
                                    .build())
                            .collect(Collectors.toList()));

                    movieProductionCountries.addAll(xmlMovie.countries.stream()
                            .map(c -> new MovieProductionCountryBuilder()
                                    .setCountry(countryDao.findByName(c))
                                    .setMovieId(xmlMovie.id)
                                    .build())
                            .collect(Collectors.toList()));

                    return new MovieBuilder()
                            .setId(xmlMovie.id)
                            .setTitle(xmlMovie.title)
                            .setPublishingYear(xmlMovie.year)
                            .setSynopsis(xmlMovie.synopsis)
                            .setCover(xmlMovie.cover)
                            .setDirector(personDao.get(xmlMovie.director.id != 0 ? xmlMovie.director.id : 1))
//                            .setDirectorId(xmlMovie.director.id != 0 ? xmlMovie.director.id : 1)
                            .setLanguage(languageDao.findByName(Objects.requireNonNullElse(xmlMovie.language, "English")))
                            .build();

                }).collect(Collectors.toList());

        movieDao.batchInsert(movies);

        loadScriptWriters(scriptwriters);
        loadTrailers(trailers);
        loadActors(actors);

        loadMovieGenres(movieGenres);
        loadMovieProductionCountries(movieProductionCountries);
    }

    @Override
    public void run(String... args) throws Exception {
        if (personDao.count() == 0)
            loadPeople();

        if (movieDao.count() == 0)
            loadMovies();
    }
}