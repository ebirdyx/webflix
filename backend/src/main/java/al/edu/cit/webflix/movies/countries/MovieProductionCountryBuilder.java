package al.edu.cit.webflix.movies.countries;

import al.edu.cit.webflix.countries.Country;

public class MovieProductionCountryBuilder {
    private final MovieProductionCountry movieProductionCountry = new MovieProductionCountry();

    public MovieProductionCountryBuilder setId(int id) {
        movieProductionCountry.setId(id);
        return this;
    }

    public MovieProductionCountryBuilder setCountry(Country country) {
        movieProductionCountry.setCountry(country);
        return this;
    }

    public MovieProductionCountryBuilder setMovieId(int movieId){
        movieProductionCountry.setMovieId(movieId);
        return this;
    }

    public MovieProductionCountry build() {
        return movieProductionCountry;
    }

}
