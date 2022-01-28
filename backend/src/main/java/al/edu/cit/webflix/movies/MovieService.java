package al.edu.cit.webflix.movies;

import al.edu.cit.webflix.users.User;
import al.edu.cit.webflix.users.UserDao;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private MovieDao movieDao;
    private UserDao userDao;

    public List<Movie> getMovies() {
        return movieDao.getAll();
    }

    public void rentMovie(int userId, int movieId) {
        Movie movie = movieDao.get(movieId);
        if (movie == null)
            return;

        User user = userDao.get(userId);
        if (user == null)
            return;

        movieDao.rentMovie(userId, movieId);
    }
}
