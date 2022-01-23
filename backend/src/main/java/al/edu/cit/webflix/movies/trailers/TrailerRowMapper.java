package al.edu.cit.webflix.movies.trailers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TrailerRowMapper implements RowMapper<Trailer> {

    @Override
    public Trailer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trailer trailer = new Trailer();

        trailer.setId(rs.getInt("id"));
        trailer.setMovieId(rs.getInt("movie_id"));
        trailer.setLink(rs.getString("link"));

        return trailer;
    }
}
