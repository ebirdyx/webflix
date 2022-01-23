package al.edu.cit.webflix.movies.trailers;

import lombok.Data;

@Data
public class Trailer {
    private int id;

    private String link;

    private  int movieId;
}
