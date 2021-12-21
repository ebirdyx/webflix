package al.edu.cit.webflix.countries;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CountryDao implements IRepository<Country> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from ProductionCountry;";
    private static final String GET_BY_ID_QUERY = "select * from ProductionCountry where id = ?;";
    private static final String COUNT_QUERY = "select * from ProductionCountry;";
    private static final String INSERT_QUERY =
            "insert into ProductionCountry (name) values(?);";
    private static final String UPDATE_QUERY =
            "update ProductionCountry set name = ? where id = ?;";
    private static final String DELETE_QUERY = "delete from ProductionCountry where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Country> getAll() {
        return jdbc.queryForList(GET_ALL_QUERY, Country.class);
    }

    @Override
    public Country get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, Country.class, id);
    }

    @Override
    public void insert(Country newItem) {
        jdbc.update(INSERT_QUERY, newItem.getName());
    }

    @Override
    public void batchInsert(List<Country> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE,
                (ps, country) -> ps.setString(1, country.getName()));
    }

    @Override
    public void update(Country updatedItem) {
        jdbc.update(
                UPDATE_QUERY,
                updatedItem.getName(),
                updatedItem.getId());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
