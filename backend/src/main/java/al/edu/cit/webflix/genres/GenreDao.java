package al.edu.cit.webflix.genres;

import al.edu.cit.webflix.common.IRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDao implements IRepository<Genre> {
    private JdbcTemplate jdbc;

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Genre get(int id) {
        return null;
    }

    @Override
    public void add(Genre item) {

    }

    @Override
    public void update(Genre person) {

    }

    @Override
    public void delete(int id) {

    }
}
