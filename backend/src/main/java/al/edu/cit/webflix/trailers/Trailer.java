package al.edu.cit.webflix.trailers;

import lombok.Data;

@Data
public class Trailer {
    private int id;

    private String link;

    private  int movieId;
}
