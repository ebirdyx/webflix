package al.edu.cit.webflix.genres;

import al.edu.cit.webflix.common.IRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDao implements IRepository<Genre> {
    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Genre> getAll() {
        return null;
    }

    @Override
    public Genre get(int id) {
        return null;
    }

    @Override
    public void insert(Genre newItem) {

    }

    @Override
    public void batchInsert(List<Genre> items) {

    }

    @Override
    public void update(Genre updatedItem) {

    }

    @Override
    public void delete(int id) {

    }
}
