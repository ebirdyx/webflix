package al.edu.cit.webflix.users.subscriptions;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class SubscriptionDao implements IRepository<Subscription> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from Subscription;";
    private static final String GET_BY_ID_QUERY = "select * from Subscription where id = ?;";
    private static final String GET_BY_CODE_QUERY = "select * from Subscription where code = ?;";
    private static final String COUNT_QUERY = "select * from Subscription;";
    private static final String INSERT_QUERY =
            "insert into Subscription " +
            "(name, code, cost, max_rentals, max_duration) " +
            "values(?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update Subscription set " +
            "name = ?, code = ?, cost = ?, max_rentals = ?, max_duration = ? " +
            "where id = ?;";
    private static final String DELETE_QUERY = "delete from Subscription where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Subscription> getAll() {
        return jdbc.query(GET_ALL_QUERY, new SubscriptionRowMapper());
    }

    @Override
    public Subscription get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, new SubscriptionRowMapper(), id);
    }

    public Subscription getByCode(String code) {
        return jdbc.queryForObject(GET_BY_CODE_QUERY, new SubscriptionRowMapper(), code);
    }

    @Override
    public void insert(Subscription newItem) {
        jdbc.update(INSERT_QUERY,
                newItem.getName(),
                newItem.getCode(),
                newItem.getCost(),
                newItem.getMaxRentals(),
                newItem.getMaxDuration());
    }

    private ParameterizedPreparedStatementSetter<Subscription> prepareBatchInsertStatement() {
        return (ps, subscription) -> {
            ps.setString(1, subscription.getName());
            ps.setString(2, subscription.getCode());
            ps.setFloat(3, subscription.getCost());
            ps.setInt(4, subscription.getMaxRentals());
            ps.setInt(5, subscription.getMaxDuration());
        };
    }

    @Override
    public void batchInsert(List<Subscription> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<Subscription> prepareUpdateStatement() {
        return (ps, subscription) -> {
            ps.setString(1, subscription.getName());
            ps.setString(2, subscription.getCode());
            ps.setFloat(3, subscription.getCost());
            ps.setInt(4, subscription.getMaxRentals());
            ps.setInt(5, subscription.getMaxDuration());
            ps.setInt(6, subscription.getId());
        };
    }

    @Override
    public void update(Subscription updatedItem) {
        jdbc.update(UPDATE_QUERY, prepareUpdateStatement());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
