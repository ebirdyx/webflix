package al.edu.cit.webflix.users;

import al.edu.cit.webflix.common.IRepository;
import al.edu.cit.webflix.configuration.PasswordEncoder;
import al.edu.cit.webflix.users.addresses.AddressDao;
import al.edu.cit.webflix.users.creditcards.CreditCardDao;
import al.edu.cit.webflix.users.customersubscriptions.CustomerSubscriptionDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserDao implements IRepository<User> {
    private JdbcTemplate jdbc;

    private AddressDao addressDao;
    private CreditCardDao creditCardDao;
    private CustomerSubscriptionDao customerSubscriptionDao;

    private PasswordEncoder passwordEncoder;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from User;";
    private static final String GET_BY_ID_QUERY = "select * from User where id = ?;";
    private static final String COUNT_QUERY = "select * from User;";
    private static final String INSERT_QUERY =
            "insert into User " +
            "(user_type, username, password, first_name, last_name, phone_no, address_id, credit_card_id) " +
            "values(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update User set " +
            "user_type = ?, username = ?, password = ?, first_name = ?, last_name = ?, phone_no = ?, address_id = ?, credit_card_id = ? " +
            "where id = ?;";
    private static final String DELETE_QUERY = "delete from User where id = ?;";

    private String hashPassword(String password) {
        return passwordEncoder
                .bCryptPasswordEncoder()
                .encode(password);
    }

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<User> getAll() {
        return jdbc.query(GET_ALL_QUERY, new UserRowMapper(addressDao, creditCardDao, customerSubscriptionDao));
    }

    @Override
    public User get(int id) {
        return jdbc.queryForObject(
                GET_BY_ID_QUERY,
                new UserRowMapper(addressDao, creditCardDao, customerSubscriptionDao),
                id);
    }

    @Override
    public void insert(User newItem) {
        jdbc.update(INSERT_QUERY,
                newItem.getUserType().toString(),
                newItem.getUsername(),
                hashPassword(newItem.getPasswordHash()),
                newItem.getFirstName(),
                newItem.getLastName(),
                newItem.getPhoneNumber(),
                newItem.getAddress().getId(),
                newItem.getCreditCard().getId());
    }

    private ParameterizedPreparedStatementSetter<User> prepareBatchInsertStatement() {
        return (ps, user) -> {
            ps.setString(1, user.getUserType().toString());
            ps.setString(2, user.getUsername());
            ps.setString(3, hashPassword(user.getPasswordHash()));
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getPhoneNumber());
            ps.setInt(7, user.getAddress().getId());
            ps.setInt(8, user.getCreditCard().getId());
        };
    }

    @Override
    public void batchInsert(List<User> items) {
        jdbc.update(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<User> prepareUpdateStatement() {
        return (ps, user) -> {
            if (user.getPasswordHash() != null) {
                user.setPasswordHash(hashPassword(user.getPasswordHash()));
            } else {
                user.setPasswordHash(get(user.getId()).getPasswordHash());
            }

            ps.setString(1, user.getUserType().toString());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPasswordHash());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getPhoneNumber());
            ps.setInt(7, user.getAddress().getId());
            ps.setInt(8, user.getCreditCard().getId());
            ps.setInt(9, user.getId());
        };
    }

    @Override
    public void update(User updatedItem) {
        jdbc.update(UPDATE_QUERY, prepareUpdateStatement(), updatedItem);
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
