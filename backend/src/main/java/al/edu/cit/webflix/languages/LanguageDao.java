package al.edu.cit.webflix.languages;

import al.edu.cit.webflix.common.IRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LanguageDao implements IRepository<Language> {
    @Override
    public List<Language> getAll() {
        return null;
    }

    @Override
    public Language get(int id) {
        return null;
    }

    @Override
    public void add(Language newItem) {

    }

    @Override
    public void update(Language updatedItem) {

    }

    @Override
    public void delete(int id) {

    }
}
