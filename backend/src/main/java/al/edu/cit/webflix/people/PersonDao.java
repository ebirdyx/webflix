package al.edu.cit.webflix.people;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static al.edu.cit.webflix.common.Utils.dateToString;

@Repository
@AllArgsConstructor
public class PersonDao implements IRepository<Person> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from People;";
    private static final String GET_BY_ID_QUERY = "select * from People where id = ?;";
    private static final String COUNT_QUERY = "select * from People;";
    private static final String INSERT_QUERY =
            "insert into People " +
            "(name, dob, bio, photo, birth_city, birth_state, birth_country) " +
            "values(?, ?, ?, ?, ?, ?, ?);";
    private static final String BATCH_INSERT_QUERY =
            "insert into People " +
                    "(id, name, dob, bio, photo, birth_city, birth_state, birth_country) " +
                    "values(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update People set " +
            "name = ?, dob = ?, bio = ?, photo = ?, birth_city = ?, birth_state = ?, birth_country = ?" +
            "where id = ?;";
    private static final String DELETE_QUERY = "delete from People where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Person> getAll() {
        return jdbc.queryForList(GET_ALL_QUERY, Person.class);
    }

    @Override
    public Person get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, Person.class, id);
    }

    private ParameterizedPreparedStatementSetter<Person> prepareInsertStatement() {
        return (ps, person) -> {
            ps.setString(1, person.getName());
            ps.setString(2, dateToString(person.getDob()));
            ps.setString(3, person.getBio());
            ps.setString(4, person.getPhoto());
            ps.setString(5, person.getBirthCity());
            ps.setString(6, person.getBirthState());
            ps.setString(7, person.getBirthCountry());
        };
    }

    @Override
    public void insert(Person newItem) {
        jdbc.update(INSERT_QUERY, prepareInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<Person> prepareBatchInsertStatement() {
        return (ps, person) -> {
            ps.setInt(1, person.getId());
            ps.setString(2, person.getName());
            ps.setString(3, dateToString(person.getDob()));
            ps.setString(4, person.getBio());
            ps.setString(5, person.getPhoto());
            ps.setString(6, person.getBirthCity());
            ps.setString(7, person.getBirthState());
            ps.setString(8, person.getBirthCountry());
        };
    }

    @Transactional
    @Override
    public void batchInsert(List<Person> items) {
        jdbc.batchUpdate(BATCH_INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<Person> prepareUpdateStatement() {
        return (ps, person) -> {
            ps.setString(1, person.getName());
            ps.setString(2, dateToString(person.getDob()));
            ps.setString(3, person.getBio());
            ps.setString(4, person.getPhoto());
            ps.setString(5, person.getBirthCity());
            ps.setString(6, person.getBirthState());
            ps.setString(7, person.getBirthCountry());
            ps.setInt(8, person.getId());
        };
    }

    @Override
    public void update(Person updatedItem) {
        jdbc.update(UPDATE_QUERY, prepareUpdateStatement());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
