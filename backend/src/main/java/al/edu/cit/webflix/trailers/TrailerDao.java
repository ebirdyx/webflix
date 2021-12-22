package al.edu.cit.webflix.trailers;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class TrailerDao implements IRepository<Trailer> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from Trailer;";
    private static final String GET_BY_ID_QUERY = "select * from Trailer where id = ?;";
    private static final String COUNT_QUERY = "select * from Trailer;";
    private static final String INSERT_QUERY =
            "insert into Trailer (link, movie_id) values(?, ?);";
    private static final String UPDATE_QUERY =
            "update Trailer set link = ? where id = ?;";
    private static final String DELETE_QUERY = "delete from Trailer where id = ?;";


    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Trailer> getAll() {
        return jdbc.queryForList(GET_ALL_QUERY, Trailer.class);
    }

    @Override
    public Trailer get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, Trailer.class, id);
    }

    @Override
    public void insert(Trailer newItem) {
        jdbc.update(INSERT_QUERY, newItem.getLink(), newItem.getMovieId());
    }

    private ParameterizedPreparedStatementSetter<Trailer> prepareBatchInsertStatement() {
        return (ps, scriptwriter) -> {
            ps.setString(1, scriptwriter.getLink());
            ps.setInt(2, scriptwriter.getMovieId());
        };
    }
    @Override
    public void batchInsert(List<Trailer> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    @Override
    public void update(Trailer updatedItem) {
        jdbc.update(UPDATE_QUERY, updatedItem.getLink());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
