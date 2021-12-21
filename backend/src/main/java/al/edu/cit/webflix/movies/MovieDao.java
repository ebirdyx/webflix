package al.edu.cit.webflix.movies;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MovieDao implements IRepository<Movie> {
    private JdbcTemplate jdbc;

    private static final String COUNT_QUERY = "select * from Movie;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Movie> getAll() {
        return null;
    }

    @Override
    public Movie get(int id) {
        return null;
    }

    @Override
    public void insert(Movie newItem) {

    }

    @Override
    public void batchInsert(List<Movie> items) {

    }

    @Override
    public void update(Movie updatedItem) {

    }

    @Override
    public void delete(int id) {

    }
}
