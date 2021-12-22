package al.edu.cit.webflix.movies.genres;

import lombok.Data;

@Data
public class MovieGenre {
    private int id;

    private int movieId;

    private int genreId;
}
