package al.edu.cit.webflix.movies;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setPublishingYear(rs.getInt("publishing_year"));
        movie.setDuration(rs.getInt("duration"));
        movie.setSynopsis(rs.getString("synopsis"));
        movie.setCover(rs.getString("cover"));
        movie.setLanguageId(rs.getInt("language_id"));
        movie.setDirectorId(rs.getInt("director_id"));
        return movie;
    }
}
