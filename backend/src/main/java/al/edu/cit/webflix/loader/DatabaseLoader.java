package al.edu.cit.webflix.loader;

import al.edu.cit.webflix.movies.actors.Actor;
import al.edu.cit.webflix.movies.actors.ActorBuilder;
import al.edu.cit.webflix.movies.actors.ActorDao;
import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.countries.CountryBuilder;
import al.edu.cit.webflix.countries.CountryDao;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.genres.GenreBuilder;
import al.edu.cit.webflix.genres.GenreDao;
import al.edu.cit.webflix.languages.Language;
import al.edu.cit.webflix.languages.LanguageBuilder;
import al.edu.cit.webflix.languages.LanguageDao;
import al.edu.cit.webflix.loader.models.clients.XMLClients;
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
import al.edu.cit.webflix.movies.scriptwriter.Scriptwriter;
import al.edu.cit.webflix.movies.scriptwriter.ScriptwriterBuilder;
import al.edu.cit.webflix.movies.scriptwriter.ScriptwriterDao;
import al.edu.cit.webflix.movies.trailers.Trailer;
import al.edu.cit.webflix.movies.trailers.TrailerBuilder;
import al.edu.cit.webflix.movies.trailers.TrailerDao;
import al.edu.cit.webflix.users.User;
import al.edu.cit.webflix.users.UserBuilder;
import al.edu.cit.webflix.users.UserDao;
import al.edu.cit.webflix.users.UserType;
import al.edu.cit.webflix.users.addresses.Address;
import al.edu.cit.webflix.users.addresses.AddressBuilder;
import al.edu.cit.webflix.users.addresses.AddressDao;
import al.edu.cit.webflix.users.creditcards.CreditCard;
import al.edu.cit.webflix.users.creditcards.CreditCardBuilder;
import al.edu.cit.webflix.users.creditcards.CreditCardDao;
import al.edu.cit.webflix.users.creditcards.CreditCardType;
import al.edu.cit.webflix.users.customersubscriptions.CustomerSubscription;
import al.edu.cit.webflix.users.customersubscriptions.CustomerSubscriptionBuilder;
import al.edu.cit.webflix.users.customersubscriptions.CustomerSubscriptionDao;
import al.edu.cit.webflix.users.subscriptions.Subscription;
import al.edu.cit.webflix.users.subscriptions.SubscriptionBuilder;
import al.edu.cit.webflix.users.subscriptions.SubscriptionDao;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static al.edu.cit.webflix.common.Utils.deserializeXmlObject;
import static al.edu.cit.webflix.common.Utils.readFileFromResources;

@Component
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
    private UserDao userDao;
    private AddressDao addressDao;
    private CreditCardDao creditCardDao;
    private SubscriptionDao subscriptionDao;
    private CustomerSubscriptionDao customerSubscriptionDao;

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

    private void createAdminUser() {
        Address address = new AddressBuilder()
                .setId(1)
                .build();

        addressDao.insert(address);

        CreditCard creditCard = new CreditCardBuilder()
                .setType(CreditCardType.MasterCard)
                .setId(1)
                .build();

        creditCardDao.insert(creditCard);

        User user = new UserBuilder()
                .setType(UserType.Employee)
                .setUsername("admin")
                .setPassword("admin")
                .setAddress(address)
                .setCreditCard(creditCard)
                .build();

        userDao.insert(user);
    }

    public void loadSubscriptions() {
        Subscription beginner = new SubscriptionBuilder()
                .setName("Beginner")
                .setCode("D")
                .setCost(5)
                .setMaxRentals(1)
                .setMaxDuration(10)
                .build();

        subscriptionDao.insert(beginner);

        Subscription intermediate = new SubscriptionBuilder()
                .setName("Intermediate")
                .setCode("I")
                .setCost(10)
                .setMaxRentals(5)
                .setMaxDuration(30)
                .build();

        subscriptionDao.insert(intermediate);

        Subscription advanced = new SubscriptionBuilder()
                .setName("Advanced")
                .setCode("AT")
                .setCost(15)
                .setMaxRentals(10)
                .setMaxDuration(365)
                .build();

        subscriptionDao.insert(advanced);
    }

    private void loadUsers() throws IOException {
        createAdminUser();
        loadSubscriptions();

        String xml = readFileFromResources("data/clients_latin1.xml");
        XMLClients xmlClients = (XMLClients) deserializeXmlObject(xml, XMLClients.class);

        xmlClients.clients
                .forEach(xmlClient -> {
                    Address address = new AddressBuilder()
                            .setId(xmlClient.index)
                            .setCivicNumber(xmlClient.getCivicNo())
                            .setStreet(xmlClient.getStreet())
                            .setProvince(xmlClient.province)
                            .setPostalCode(xmlClient.postalCode)
                            .build();

                    addressDao.insert(address);

                    CreditCard creditCard = new CreditCardBuilder()
                            .setId(xmlClient.index)
                            .setNumber(xmlClient.creditCard.cardNumber)
                            .setType(CreditCardType.valueOf(xmlClient.creditCard.cardType))
                            .setExpirationMonth(xmlClient.creditCard.expirationMonth)
                            .setExpirationYear(xmlClient.creditCard.expirationYear)
                            .build();

                    creditCardDao.insert(creditCard);

                    User user = new UserBuilder()
                            .setType(UserType.Customer)
                            .setUsername(xmlClient.email)
                            .setPassword(xmlClient.password)
                            .setFirstName(xmlClient.firstName)
                            .setLastName(xmlClient.lastName)
                            .setPhoneNumber(xmlClient.phone)
                            .setAddress(address)
                            .setCreditCard(creditCard)
                            .build();

                    userDao.insert(user);

                    CustomerSubscription customerSubscription = new CustomerSubscriptionBuilder()
                            .setCustomerId(user.getId())
                            .setSubscription(subscriptionDao.getByCode(xmlClient.subscription))
                            .setStartDate(new Date(Calendar.getInstance().getTime().getTime()))
                            .build();

                    customerSubscriptionDao.insert(customerSubscription);
                });
    }

    @Override
    public void run(String... args) throws Exception {
        if (personDao.count() == 0)
            loadPeople();

        if (movieDao.count() == 0)
            loadMovies();

        if (userDao.count() == 0)
            loadUsers();
    }
}