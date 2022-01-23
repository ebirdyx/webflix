package al.edu.cit.webflix.scriptwriter;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScriptwriterRowMapper implements RowMapper<Scriptwriter> {

    @Override
    public Scriptwriter mapRow(ResultSet rs, int rowNum) throws SQLException {
        Scriptwriter scriptwriter = new Scriptwriter();

        scriptwriter.setId(rs.getInt("id"));
        scriptwriter.setMovieId(rs.getInt("movie_id"));
        scriptwriter.setName(rs.getString("name"));

        return scriptwriter;
    }
}
