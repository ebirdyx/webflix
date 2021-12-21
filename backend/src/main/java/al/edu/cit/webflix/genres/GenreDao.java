package al.edu.cit.webflix.genres;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class GenreDao implements IRepository<Genre> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from Genre;";
    private static final String GET_BY_ID_QUERY = "select * from Genre where id = ?;";
    private static final String COUNT_QUERY = "select * from Genre;";
    private static final String INSERT_QUERY =
            "insert into Genre (name) values(?);";
    private static final String UPDATE_QUERY =
            "update Genre set name = ? where id = ?;";
    private static final String DELETE_QUERY = "delete from Genre where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.queryForList(GET_ALL_QUERY, Genre.class);
    }

    @Override
    public Genre get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, Genre.class, id);
    }

    @Override
    public void insert(Genre newItem) {
        jdbc.update(INSERT_QUERY, newItem.getName());
    }

    @Override
    public void batchInsert(List<Genre> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE,
                (ps, genre) -> ps.setString(1, genre.getName()));
    }

    @Override
    public void update(Genre updatedItem) {
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
