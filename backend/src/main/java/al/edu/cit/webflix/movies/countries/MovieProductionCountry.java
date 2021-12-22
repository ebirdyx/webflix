package al.edu.cit.webflix.movies.countries;

import lombok.Data;

@Data
public class MovieProductionCountry {
    private int id;

    private int countryId;

    private int movieId;
}
