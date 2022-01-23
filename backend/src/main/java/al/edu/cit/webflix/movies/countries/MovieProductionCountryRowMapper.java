package al.edu.cit.webflix.movies.countries;

import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.countries.CountryDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class MovieProductionCountryRowMapper implements RowMapper<MovieProductionCountry> {
    private CountryDao countryDao;

    @Override
    public MovieProductionCountry mapRow(ResultSet rs, int rowNum) throws SQLException {
        MovieProductionCountry movieProductionCountry = new MovieProductionCountry();

        movieProductionCountry.setId(rs.getInt("id"));
        movieProductionCountry.setMovieId(rs.getInt("movie_id"));

        int countryId = rs.getInt("country_id");
        Country country = countryDao.get(countryId);
        movieProductionCountry.setCountry(country);

        return movieProductionCountry;
    }
}
