package al.edu.cit.webflix.movies.countries;

import al.edu.cit.webflix.countries.Country;
import lombok.Data;

@Data
public class MovieProductionCountry {
    private int id;

    private Country country;

    private int movieId;
}
