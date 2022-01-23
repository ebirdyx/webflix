package al.edu.cit.webflix.movies;

import al.edu.cit.webflix.actors.ActorDao;
import al.edu.cit.webflix.common.IRepository;
import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.genres.GenreDao;
import al.edu.cit.webflix.languages.LanguageDao;
import al.edu.cit.webflix.movies.countries.MovieProductionCountryDao;
import al.edu.cit.webflix.movies.genres.MovieGenreDao;
import al.edu.cit.webflix.people.PersonDao;
import al.edu.cit.webflix.scriptwriter.ScriptwriterDao;
import al.edu.cit.webflix.trailers.TrailerDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class MovieDao implements IRepository<Movie> {
    private JdbcTemplate jdbc;
    private LanguageDao languageDao;
    private PersonDao personDao;
    private ActorDao actorDao;
    private TrailerDao trailerDao;
    private MovieProductionCountryDao movieProductionCountryDao;
    private MovieGenreDao movieGenreDao;
    private ScriptwriterDao scriptwriterDao;

    // Don't use directly, use getMapper instead (Singleton)
    private final MovieRowMapper _mapper = new MovieRowMapper(
            languageDao,
            personDao,
            actorDao,
            trailerDao,
            movieProductionCountryDao,
            movieGenreDao,
            scriptwriterDao
    );

//    private MovieRowMapper getMapper() {
//        if (_mapper == null)
//            _mapper = new MovieRowMapper(
//                    languageDao,
//                    personDao,
//                    actorDao,
//                    trailerDao,
//                    movieProductionCountryDao,
//                    movieGenreDao,
//                    scriptwriterDao
//            );
//
//        return _mapper;
//    }

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from Movie;";
    private static final String GET_BY_ID_QUERY = "select * from Movie where id = ?;";
    private static final String COUNT_QUERY = "select * from Movie;";
    private static final String INSERT_QUERY =
            "insert into Movie " +
                    "(title, publishing_year, duration, synopsis, cover, language_id, director_id) " +
                    "values(?, ?, ?, ?, ?, ?, ?);";
    private static final String BATCH_INSERT_QUERY =
            "insert into Movie " +
                    "(id, title, publishing_year, duration, synopsis, cover, language_id, director_id) " +
                    "values(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_QUERY =
            "update Movie set " +
                    "title = ?, publishing_year = ?, duration = ?, synopsis = ?, cover = ?, language_id = ?, director_id = ?" +
                    "where id = ?;";
    private static final String DELETE_QUERY = "delete from Movie where id = ?;";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Movie> getAll() {
        return jdbc.query(GET_ALL_QUERY, _mapper);
    }

    @Override
    public Movie get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, _mapper, id);
    }

    private ParameterizedPreparedStatementSetter<Movie> prepareInsertStatement() {
        return (ps, movie) -> {
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getPublishingYear());
            ps.setInt(3, movie.getDuration());
            ps.setString(4, movie.getSynopsis());
            ps.setString(5, movie.getCover());
            ps.setInt(6, movie.getLanguage() != null ? movie.getLanguage().getId() : null);
            ps.setInt(7, movie.getDirector() != null ? movie.getDirector().getId(): null);
        };
    }

    @Override
    public void insert(Movie newItem) {
        jdbc.update(INSERT_QUERY, prepareInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<Movie> prepareBatchInsertStatement() {
        return (ps, movie) -> {
            ps.setInt(1, movie.getId());
            ps.setString(2, movie.getTitle());
            ps.setInt(3, movie.getPublishingYear());
            ps.setInt(4, movie.getDuration());
            ps.setString(5, movie.getSynopsis());
            ps.setString(6, movie.getCover());
            ps.setInt(7, movie.getLanguage() != null ? movie.getLanguage().getId() : null);
            ps.setInt(8, movie.getDirector() != null ? movie.getDirector().getId(): null);
        };
    }

    @Override
    public void batchInsert(List<Movie> items) {
        jdbc.batchUpdate(BATCH_INSERT_QUERY, items, BATCH_SIZE, prepareBatchInsertStatement());
    }

    private ParameterizedPreparedStatementSetter<Movie> prepareUpdateStatement() {
        return (ps, movie) -> {
            ps.setString(1, movie.getTitle());
            ps.setInt(2, movie.getPublishingYear());
            ps.setInt(3, movie.getDuration());
            ps.setString(4, movie.getSynopsis());
            ps.setString(5, movie.getCover());
            ps.setInt(6, movie.getLanguage() != null ? movie.getLanguage().getId() : null);
            ps.setInt(7, movie.getDirector() != null ? movie.getDirector().getId(): null);
            ps.setInt(8, movie.getId());
        };
    }

    @Override
    public void update(Movie updatedItem) {
        jdbc.update(UPDATE_QUERY, prepareUpdateStatement());
    }

    @Override
    public void delete(int id) {
        jdbc.update(DELETE_QUERY, id);
    }
}
