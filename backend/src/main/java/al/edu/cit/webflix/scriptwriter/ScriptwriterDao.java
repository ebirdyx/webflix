package al.edu.cit.webflix.scriptwriter;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ScriptwriterDao implements IRepository<Scriptwriter> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from Scriptwriter;";
    private static final String GET_BY_ID_QUERY = "select * from Scriptwriter where id = ?;";
    private static final String GET_BY_MOVIE_ID_QUERY = "select * from Scriptwriter where movie_id = ?;";
    private static final String COUNT_QUERY = "select * from Scriptwriter;";
    private static final String INSERT_QUERY =
            "insert into Scriptwriter (name, movie_id) values(?, ?);";
    private static final String UPDATE_QUERY =
            "update Scriptwriter set name = ? where id = ?;";
    private static final String DELETE_QUERY = "delete from Scriptwriter where id = ?;";


    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Scriptwriter> getAll() {
        return jdbc.query(GET_ALL_QUERY, new ScriptwriterRowMapper());
    }

    @Override
    public Scriptwriter get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, new ScriptwriterRowMapper(), id);
    }

    @Override
    public void insert(Scriptwriter newItem) {
        jdbc.update(INSERT_QUERY, newItem.getName(), newItem.getMovieId());
    }

    private ParameterizedPreparedStatementSetter<Scriptwriter> prepareBatchInsertStatement() {
        return (ps, scriptwriter) -> {
            ps.setString(1, scriptwriter.getName());
            ps.setInt(2, scriptwriter.getMovieId());
        };
    }
    @Override
    public void batchInsert(List<Scriptwriter> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    @Override
    public void update(Scriptwriter updatedItem) {
        jdbc.update(UPDATE_QUERY, updatedItem.getName());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }

    public List<Scriptwriter> getMovieScriptWriters(int movieId) {
        return jdbc.query(GET_BY_MOVIE_ID_QUERY, new ScriptwriterRowMapper(), movieId);
    }
}
