package al.edu.cit.webflix.movies;

import lombok.Data;

@Data
public class RentMovieRequest {
    private int movieId;
    private int userId;
}
