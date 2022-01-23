package al.edu.cit.webflix.actors;

import al.edu.cit.webflix.people.Person;
import al.edu.cit.webflix.people.PersonDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class ActorRowMapper implements RowMapper<Actor> {
    private PersonDao personDao;

    @Override
    public Actor mapRow(ResultSet rs, int rowNum) throws SQLException {
        Actor actor = new Actor();

        actor.setId(rs.getInt("id"));
        actor.setMovieId(rs.getInt("movie_id"));
        actor.setCharacterName(rs.getString("character_name"));

        int personId = rs.getInt("person_id");
        Person person = personDao.get(personId);
        actor.setPerson(person);

        return actor;
    }
}
