package al.edu.cit.webflix.languages;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LanguageRowMapper implements RowMapper<Language> {
    @Override
    public Language mapRow(ResultSet rs, int rowNum) throws SQLException {
        Language language = new Language();
        language.setId(rs.getInt("id"));
        language.setName(rs.getString("name"));
        return language;
    }
}
