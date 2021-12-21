package al.edu.cit.webflix.people;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PersonDao implements IRepository<Person> {
    @Override
    public List<Person> getAll() {
        return null;
    }

    @Override
    public Person get(int id) {
        return null;
    }

    @Override
    public void add(Person newItem) {

    }

    @Override
    public void update(Person updatedItem) {

    }

    @Override
    public void delete(int id) {

    }
}
