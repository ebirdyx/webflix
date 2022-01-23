package al.edu.cit.webflix.movies.countries;

import al.edu.cit.webflix.common.IRepository;
import al.edu.cit.webflix.countries.Country;
import al.edu.cit.webflix.countries.CountryDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MovieProductionCountryDao implements IRepository<MovieProductionCountry> {
    private JdbcTemplate jdbc;
    private CountryDao countryDao;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from MovieProductionCountry;";
    private static final String GET_BY_ID_QUERY = "select * from MovieProductionCountry where id = ?;";
    private static final String GET_BY_MOVIE_ID_QUERY = "select * from MovieProductionCountry where movie_id = ?;";
    private static final String COUNT_QUERY = "select * from MovieProductionCountry;";
    private static final String INSERT_QUERY =
            "insert into MovieProductionCountry (country_id, movie_id) values(?, ?);";
    private static final String DELETE_QUERY = "delete from MovieProductionCountry where id = ?;";


    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<MovieProductionCountry> getAll() {
        return jdbc.query(GET_ALL_QUERY, new MovieProductionCountryRowMapper(countryDao));
    }

    @Override
    public MovieProductionCountry get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, new MovieProductionCountryRowMapper(countryDao), id);
    }

    @Override
    public void insert(MovieProductionCountry newItem) {
        jdbc.update(INSERT_QUERY, newItem.getCountry().getId(), newItem.getMovieId());
    }

    private ParameterizedPreparedStatementSetter<MovieProductionCountry> prepareBatchInsertStatement() {
        return (ps, movieProductionCountry) -> {
            ps.setInt(1, movieProductionCountry.getCountry().getId());
            ps.setInt(2, movieProductionCountry.getMovieId());
        };
    }
    @Override
    public void batchInsert(List<MovieProductionCountry> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    @Override
    public void update(MovieProductionCountry updatedItem) {
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }

    public List<Country> getMovieProductionCountries(int movieId) {
        return jdbc.query(GET_BY_MOVIE_ID_QUERY, new MovieProductionCountryRowMapper(countryDao), movieId)
                .stream()
                .map(MovieProductionCountry::getCountry)
                .collect(Collectors.toList());
    }
}
