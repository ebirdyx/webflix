package al.edu.cit.webflix.movies;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/v1/movies")
public class MovieController {
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> getMovies() {
        return ResponseEntity.ok(movieService.getMovies());
    }

    @PostMapping("/rent")
    public ResponseEntity rentMovie(@RequestBody RentMovieRequest request) {
        movieService.rentMovie(request.getUserId(), request.getMovieId());
        return ResponseEntity.ok(null);
    }
}
