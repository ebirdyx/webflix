package al.edu.cit.webflix.languages;

import al.edu.cit.webflix.common.IRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LanguageDao implements IRepository<Language> {
    @Override
    public int count() {
        return 0;
    }

    @Override
    public List<Language> getAll() {
        return null;
    }

    @Override
    public Language get(int id) {
        return null;
    }

    @Override
    public void insert(Language newItem) {

    }

    @Override
    public void batchInsert(List<Language> items) {

    }

    @Override
    public void update(Language updatedItem) {

    }

    @Override
    public void delete(int id) {

    }
}
