package al.edu.cit.webflix.people;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setId(rs.getInt("id"));
        person.setName(rs.getString("name"));
        person.setDob(rs.getDate("dob"));
        person.setBio(rs.getString("bio"));
        person.setBirthCity(rs.getString("birth_city"));
        person.setBirthState(rs.getString("birth_state"));
        person.setBirthCountry(rs.getString("birth_country"));

        return person;
    }
}
