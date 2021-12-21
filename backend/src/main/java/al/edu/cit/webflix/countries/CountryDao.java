package al.edu.cit.webflix.countries;

import al.edu.cit.webflix.common.IRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDao implements IRepository<Country> {
    @Override
    public List<Country> getAll() {
        return null;
    }

    @Override
    public Country get(int id) {
        return null;
    }

    @Override
    public void add(Country newItem) {

    }

    @Override
    public void update(Country updatedItem) {

    }

    @Override
    public void delete(int id) {

    }
}
