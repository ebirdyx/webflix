package al.edu.cit.webflix.users.customersubscriptions;

import al.edu.cit.webflix.common.IRepository;
import al.edu.cit.webflix.users.subscriptions.SubscriptionDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CustomerSubscriptionDao implements IRepository<CustomerSubscription> {
    private JdbcTemplate jdbc;

    private SubscriptionDao subscriptionDao;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from CustomerSubscription;";
    private static final String GET_BY_ID_QUERY = "select * from CustomerSubscription where id = ?;";
    private static final String GET_BY_USER_ID_QUERY = "select * from CustomerSubscription where customer_id = ?;";
    private static final String COUNT_QUERY = "select * from CustomerSubscription;";
    private static final String INSERT_QUERY =
            "insert into CustomerSubscription (start_date, end_date, subscription_id, customer_id) values(?, ?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update CustomerSubscription set " +
                    "start_date = ?, end_date = ?, subscription_id = ?, customer_id = ? " +
                    "where id = ?;";
    private static final String DELETE_QUERY = "delete from CustomerSubscription where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<CustomerSubscription> getAll() {
        return jdbc.query(GET_ALL_QUERY, new CustomerSubscriptionRowMapper(subscriptionDao));
    }

    @Override
    public CustomerSubscription get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, new CustomerSubscriptionRowMapper(subscriptionDao), id);
    }

    public List<CustomerSubscription> getByUserId(int userId) {
        return jdbc.query(GET_BY_USER_ID_QUERY, new CustomerSubscriptionRowMapper(subscriptionDao), userId);
    }

    @Override
    public void insert(CustomerSubscription newItem) {
        jdbc.update(INSERT_QUERY,
                newItem.getStartDate(),
                newItem.getEndDate(),
                newItem.getSubscription().getId(),
                newItem.getCustomerId());
    }

    private ParameterizedPreparedStatementSetter<CustomerSubscription> prepareBatchInsertStatement() {
        return (ps, customerSubscription) -> {
            ps.setDate(1, customerSubscription.getStartDate());
            ps.setDate(2, customerSubscription.getEndDate());
            ps.setInt(3, customerSubscription.getSubscription().getId());
            ps.setInt(4, customerSubscription.getCustomerId());
        };
    }

    @Override
    public void batchInsert(List<CustomerSubscription> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<CustomerSubscription> prepareUpdateStatement() {
        return (ps, customerSubscription) -> {
            ps.setDate(1, customerSubscription.getStartDate());
            ps.setDate(2, customerSubscription.getEndDate());
            ps.setInt(3, customerSubscription.getSubscription().getId());
            ps.setInt(4, customerSubscription.getCustomerId());
            ps.setInt(5, customerSubscription.getId());
        };
    }

    @Override
    public void update(CustomerSubscription updatedItem) {
        jdbc.update(UPDATE_QUERY, prepareUpdateStatement());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
