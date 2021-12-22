package al.edu.cit.webflix.movies.countries;

public class MovieProductionCountryBuilder {
    private final MovieProductionCountry movieProductionCountry = new MovieProductionCountry();

    public MovieProductionCountryBuilder setId(int id) {
        movieProductionCountry.setId(id);
        return this;
    }

    public MovieProductionCountryBuilder setCountryId(int countryId) {
        movieProductionCountry.setCountryId(countryId);
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
