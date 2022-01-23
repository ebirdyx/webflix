package al.edu.cit.webflix.users.addresses;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AddressDao implements IRepository<Address> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from Address;";
    private static final String GET_BY_ID_QUERY = "select * from Address where id = ?;";
    private static final String COUNT_QUERY = "select * from Address;";
    private static final String INSERT_QUERY =
            "insert into Address (civic_no, city, street, province, post_code) values(?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update Address set " +
            "civic_no = ?, city = ?, street = ?, province = ?, post_code = ? " +
            "where id = ?;";
    private static final String DELETE_QUERY = "delete from Address where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Address> getAll() {
        return jdbc.query(GET_ALL_QUERY, new AddressRowMapper());
    }

    @Override
    public Address get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, new AddressRowMapper(), id);
    }

    @Override
    public void insert(Address newItem) {
        jdbc.update(INSERT_QUERY,
                newItem.getCivicNumber(),
                newItem.getCity(),
                newItem.getStreet(),
                newItem.getProvince(),
                newItem.getPostalCode());
    }

    private ParameterizedPreparedStatementSetter<Address> prepareBatchInsertStatement() {
        return (ps, address) -> {
            ps.setInt(1, address.getCivicNumber());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getProvince());
            ps.setString(5, address.getPostalCode());
        };
    }

    @Override
    public void batchInsert(List<Address> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<Address> prepareUpdateStatement() {
        return (ps, address) -> {
            ps.setInt(1, address.getCivicNumber());
            ps.setString(2, address.getCity());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getProvince());
            ps.setString(5, address.getPostalCode());
            ps.setInt(6, address.getId());
        };
    }

    @Override
    public void update(Address updatedItem) {
        jdbc.update(UPDATE_QUERY, prepareUpdateStatement());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
