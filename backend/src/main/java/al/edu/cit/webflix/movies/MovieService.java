package al.edu.cit.webflix.movies;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService {
    private MovieDao movieDao;

    public List<Movie> getMovies() {
        return movieDao.getAll();
    }
}
