package al.edu.cit.webflix.people;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class PersonDao {
    private JdbcTemplate jdbc;

    public List<Person> getAll() {
        return new ArrayList<>();
    }

    public Person get(int id) {
        return new Person();
    }

    public void add(Person person) {

    }

    public void update(Person person) {

    }

    public void delete(int id) {

    }
}
