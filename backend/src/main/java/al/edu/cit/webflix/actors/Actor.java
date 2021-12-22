package al.edu.cit.webflix.actors;

import lombok.Data;

@Data
public class Actor {
    private int id;

    private int personId;

    private int movieId;

    private String characterName;
}
