package al.edu.cit.webflix.movies.genres;

import al.edu.cit.webflix.genres.Genre;
import al.edu.cit.webflix.genres.GenreDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class MovieGenreRowMapper implements RowMapper<MovieGenre> {
    private GenreDao genreDao;

    @Override
    public MovieGenre mapRow(ResultSet rs, int rowNum) throws SQLException {
        MovieGenre movieGenre = new MovieGenre();

        movieGenre.setId(rs.getInt("id"));
        movieGenre.setMovieId(rs.getInt("movie_id"));

        int genreId = rs.getInt("genre_id");
        Genre genre = genreDao.get(genreId);
        movieGenre.setGenre(genre);

        return movieGenre;
    }
}
