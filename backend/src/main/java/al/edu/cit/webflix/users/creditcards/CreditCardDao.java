package al.edu.cit.webflix.users.creditcards;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CreditCardDao implements IRepository<CreditCard> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from CreditCard;";
    private static final String GET_BY_ID_QUERY = "select * from CreditCard where id = ?;";
    private static final String COUNT_QUERY = "select * from CreditCard;";
    private static final String INSERT_QUERY =
            "insert into CreditCard (no, type, expiration_date_month, expiration_date_year, cvv) values(?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update CreditCard set " +
                    "no = ?, type = ?, expiration_date_month = ?, expiration_date_year = ?, cvv = ? " +
                    "where id = ?;";
    private static final String DELETE_QUERY = "delete from CreditCard where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<CreditCard> getAll() {
        return jdbc.query(GET_ALL_QUERY, new CreditCardRowMapper());
    }

    @Override
    public CreditCard get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, new CreditCardRowMapper(), id);
    }

    @Override
    public void insert(CreditCard newItem) {
        jdbc.update(INSERT_QUERY,
                newItem.getNumber(),
                newItem.getType().toString(),
                newItem.getExpirationMonth(),
                newItem.getExpirationYear(),
                newItem.getCvv());
    }

    private ParameterizedPreparedStatementSetter<CreditCard> prepareBatchInsertStatement() {
        return (ps, creditCard) -> {
            ps.setString(1, creditCard.getNumber());
            ps.setString(2, creditCard.getType().toString());
            ps.setInt(3, creditCard.getExpirationMonth());
            ps.setInt(4, creditCard.getExpirationYear());
            ps.setString(5, creditCard.getCvv());
        };
    }

    @Override
    public void batchInsert(List<CreditCard> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<CreditCard> prepareUpdateStatement() {
        return (ps, creditCard) -> {
            ps.setString(1, creditCard.getNumber());
            ps.setString(2, creditCard.getType().toString());
            ps.setInt(3, creditCard.getExpirationMonth());
            ps.setInt(4, creditCard.getExpirationYear());
            ps.setString(5, creditCard.getCvv());
            ps.setInt(1, creditCard.getId());
        };
    }

    @Override
    public void update(CreditCard updatedItem) {
        jdbc.update(UPDATE_QUERY, prepareUpdateStatement());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
