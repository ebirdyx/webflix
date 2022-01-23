package al.edu.cit.webflix.movies.genres;

import al.edu.cit.webflix.genres.Genre;
import lombok.Data;

@Data
public class MovieGenre {
    private int id;

    private int movieId;

    private Genre genre;
}
