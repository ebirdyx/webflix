package al.edu.cit.webflix.actors;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class ActorDao implements IRepository<Actor> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from PersonRolePlayed;";
    private static final String GET_BY_ID_QUERY = "select * from PersonRolePlayed where id = ?;";
    private static final String COUNT_QUERY = "select * from PersonRolePlayed;";
    private static final String INSERT_QUERY =
            "insert into PersonRolePlayed (character_name, person_id, movie_id) values(?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update PersonRolePlayed set character_name = ? where id = ?;";
    private static final String DELETE_QUERY = "delete from PersonRolePlayed where id = ?;";


    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Actor> getAll() {
        return jdbc.queryForList(GET_ALL_QUERY, Actor.class);
    }

    @Override
    public Actor get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, Actor.class, id);
    }

    @Override
    public void insert(Actor newItem) {
        jdbc.update(INSERT_QUERY, newItem.getCharacterName(), newItem.getPersonId(), newItem.getMovieId());
    }

    private ParameterizedPreparedStatementSetter<Actor> prepareBatchInsertStatement() {
        return (ps, scriptwriter) -> {
            ps.setString(1, scriptwriter.getCharacterName());
            ps.setInt(2, scriptwriter.getPersonId());
            ps.setInt(3, scriptwriter.getMovieId());
        };
    }
    @Override
    public void batchInsert(List<Actor> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    @Override
    public void update(Actor updatedItem) {
        jdbc.update(UPDATE_QUERY, updatedItem.getCharacterName());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }

}
