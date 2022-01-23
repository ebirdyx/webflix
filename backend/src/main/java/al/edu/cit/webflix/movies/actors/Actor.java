package al.edu.cit.webflix.movies.actors;

import al.edu.cit.webflix.people.Person;
import lombok.Data;

@Data
public class Actor {
    private int id;

    private Person person;

    private int movieId;

    private String characterName;
}
