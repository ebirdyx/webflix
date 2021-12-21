package al.edu.cit.webflix.loader;

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
import al.edu.cit.webflix.movies.MovieDao;
import al.edu.cit.webflix.people.Person;
import al.edu.cit.webflix.people.PersonBuilder;
import al.edu.cit.webflix.people.PersonDao;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public void loadMovies() throws IOException {
        String xml = readFileFromResources("data/movies_latin1.xml");
        XMLMovies movies = (XMLMovies) deserializeXmlObject(xml, XMLMovies.class);

        if (genreDao.count() == 0)
            loadGenres(movies);

        if (countryDao.count() == 0)
            loadCountries(movies);

        if (languageDao.count() == 0)
            loadLanguages(movies);

//        movies.movies.stream().forEach(xmlMovie -> {
//            int languageIndex = 0;
//            for (String language : languages) {
//                languageIndex++;
//                if (language.equals(xmlMovie.language)) break;
//            }
//
//            String movieQuery = "insert into Movie(id, title, publishing_year, duration, synopsis,  cover, language_id, director_id)"
//                    + "values(" + xmlMovie.id + ", " + xmlMovie.getTitle() + ", " + xmlMovie.year + ", " + xmlMovie.duration + ", "
//                    + xmlMovie.getEscapedSynopsy() + ", " + xmlMovie.getEscapedCover() + ", " + languageIndex + ", " +
//                    xmlMovie.getDirector().id + "');";
//        });
    }

    @Override
    public void run(String... args) throws Exception {
        if (personDao.count() == 0)
            loadPeople();

        if (movieDao.count() == 0)
            loadMovies();
    }
}