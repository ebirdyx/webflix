package al.edu.cit.webflix.movies.genres;

import al.edu.cit.webflix.common.IRepository;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.genres.GenreDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class MovieGenreDao implements IRepository<MovieGenre> {
    private JdbcTemplate jdbc;
    private GenreDao genreDao;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from MovieGenre;";
    private static final String GET_BY_ID_QUERY = "select * from MovieGenre where id = ?;";
    private static final String GET_BY_MOVIE_ID_QUERY = "select * from MovieGenre where movie_id = ?;";
    private static final String COUNT_QUERY = "select * from MovieGenre;";
    private static final String INSERT_QUERY =
            "insert into MovieGenre (movie_id, genre_id) values(?, ?);";
    private static final String DELETE_QUERY = "delete from MovieGenre where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<MovieGenre> getAll() {
        return jdbc.query(GET_ALL_QUERY, new MovieGenreRowMapper(genreDao));
    }

    @Override
    public MovieGenre get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, new MovieGenreRowMapper(genreDao), id);
    }

    @Override
    public void insert(MovieGenre newItem) {
        jdbc.update(INSERT_QUERY, newItem.getMovieId(), newItem.getGenre().getId());
    }

    private ParameterizedPreparedStatementSetter<MovieGenre> prepareBatchInsertStatement() {
        return (ps, movieGenre) -> {
            ps.setInt(1, movieGenre.getMovieId());
            ps.setInt(2, movieGenre.getGenre().getId());
        };
    }
    @Override
    public void batchInsert(List<MovieGenre> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    @Override
    public void update(MovieGenre updatedItem) {
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }

    public List<Genre> getMovieGenres(int movieId) {
        return jdbc.query(GET_BY_MOVIE_ID_QUERY, new MovieGenreRowMapper(genreDao), movieId)
                .stream()
                .map(MovieGenre::getGenre)
                .collect(Collectors.toList());
    }
}
