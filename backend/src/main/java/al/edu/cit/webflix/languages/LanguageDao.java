package al.edu.cit.webflix.languages;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class LanguageDao implements IRepository<Language> {
    private JdbcTemplate jdbc;

    private static final int BATCH_SIZE = 500;

    private static final String GET_ALL_QUERY = "select * from MovieLanguages;";
    private static final String GET_BY_ID_QUERY = "select * from MovieLanguages where id = ?;";
    private static final String COUNT_QUERY = "select * from MovieLanguages;";
    private static final String INSERT_QUERY =
            "insert into MovieLanguages (name) values(?);";
    private static final String UPDATE_QUERY =
            "update MovieLanguages set name = ? where id = ?;";
    private static final String DELETE_QUERY = "delete from MovieLanguages where id = ?;";
    private static final String FIND_BY_NAME = "select * from MovieLanguages where name = ?";

    @Override
    public int count() {
        RowCountCallbackHandler countCallback = new RowCountCallbackHandler();
        jdbc.query(COUNT_QUERY, countCallback);
        return countCallback.getRowCount();
    }

    @Override
    public List<Language> getAll() {
        return jdbc.queryForList(GET_ALL_QUERY, Language.class);
    }

    @Override
    public Language get(int id) {
        return jdbc.queryForObject(GET_BY_ID_QUERY, Language.class, id);
    }

    public Language findByName(String name) {
        return jdbc.queryForObject(FIND_BY_NAME, new LanguageRowMapper(), name);
    }

    @Override
    public void insert(Language newItem) {
        jdbc.update(INSERT_QUERY, newItem.getName());
    }

    @Override
    public void batchInsert(List<Language> items) {
        jdbc.batchUpdate(INSERT_QUERY, items, BATCH_SIZE,
                (ps, language) -> ps.setString(1, language.getName()));
    }

    @Override
    public void update(Language updatedItem) {
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
