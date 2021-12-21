package al.edu.cit.webflix.loader;

import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.countries.CountryDao;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.genres.GenreDao;
import al.edu.cit.webflix.languages.Language;
import al.edu.cit.webflix.languages.LanguageDao;
import al.edu.cit.webflix.loader.models.movies.XMLMovies;
import al.edu.cit.webflix.loader.models.people.XMLPeople;
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

    public void loadPeople() {
        try {
            String xml = readFileFromResources("data/people_latin1.xml");
            XMLPeople people = (XMLPeople) deserializeXmlObject(xml, XMLPeople.class);
            List<Person> p = people.people.stream().map(person ->
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
            personDao.batchInsert(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMovies() {
        String xml = null;
        try {
            xml = readFileFromResources("data/movies_latin1.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }

        XMLMovies movies = null;
        try {
            movies = (XMLMovies) deserializeXmlObject(xml, XMLMovies.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Set<String> genres = new HashSet<>();

        movies.movies.stream()
                .forEach(xmlMovie -> xmlMovie.genres.stream()
                        .forEach(genre -> genres.add(genre)));

        genres.stream().forEach(genre -> {
            String query = "insert into Genre(name) values('" + genre + "');";
            genreDao.insert(new Genre());
        });

        Set<String> countries = new HashSet<>();

        movies.movies.stream()
                .forEach(xmlMovie -> xmlMovie.countries.stream()
                        .forEach(country -> countries.add(country)));

        countries.stream().forEach(country -> {
            String query = "insert into ProductionCountry(name) values('" + country + "');";
            countryDao.insert(new Country());
        });

        Set<String> languages = new HashSet<>();

        movies.movies.stream()
                .forEach(xmlMovie -> {
                    if (xmlMovie.language == null) return;
                    languages.add(xmlMovie.language);
                });

        languages.stream().forEach(language -> {
            String query = "insert into MovieLanguages(name) values('" + language + "');";
            languageDao.insert(new Language());
        });

        movies.movies.stream().forEach(xmlMovie -> {
            int languageIndex = 0;
            for (String language : languages) {
                languageIndex++;
                if (language.equals(xmlMovie.language)) break;
            }

            String movieQuery = "insert into Movie(id, title, publishing_year, duration, synopsis,  cover, language_id, director_id)"
                    + "values(" + xmlMovie.id + ", " + xmlMovie.getTitle() + ", " + xmlMovie.year + ", " + xmlMovie.duration + ", "
                    + xmlMovie.getEscapedSynopsy() + ", " + xmlMovie.getEscapedCover() + ", " + languageIndex + ", " +
                    xmlMovie.getDirector().id + "');";
        });
    }

    @Override
    public void run(String... args) throws Exception {
        if (personDao.count() == 0)
            loadPeople();

//        loadMovies();
    }
}