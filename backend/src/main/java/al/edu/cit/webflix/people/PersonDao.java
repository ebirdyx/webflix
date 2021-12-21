package al.edu.cit.webflix.people;

import al.edu.cit.webflix.common.IRepository;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static al.edu.cit.webflix.common.Utils.dateToString;
import static al.edu.cit.webflix.common.Utils.surroundWithSingleQuotes;

@Repository
@AllArgsConstructor
public class PersonDao implements IRepository<Person> {
    private JdbcTemplate jdbc;

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
        String query = String.format(
                "insert into People (id, name, dob, bio, photo, birth_city, birth_state, birth_country) " +
                "values(%d, %s, %s, %s, %s, %s, %s, %s);",
                newItem.getId(),
                surroundWithSingleQuotes(newItem.getName()),
                "STR_TO_DATE(" + surroundWithSingleQuotes(dateToString(newItem.getDob())) + ", '%Y-%m-%d')",
                surroundWithSingleQuotes(newItem.getBio()),
                surroundWithSingleQuotes(newItem.getPhoto()),
                surroundWithSingleQuotes(newItem.getBirthCity()),
                surroundWithSingleQuotes(newItem.getBirthState()),
                surroundWithSingleQuotes(newItem.getBirthCountry())
        );

        jdbc.execute(query);
    }

    @Override
    public void update(Person updatedItem) {

    }

    @Override
    public void delete(int id) {

    }
}
