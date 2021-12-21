package al.edu.cit.webflix.loader.models.people;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static al.edu.cit.webflix.Utils.toSQLString;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLBirth {
    public String dob;

    public String lob;

    public String getSQLDob(){
        if (dob == null) return "NULL";
        return "STR_TO_DATE(" + toSQLString(dob) + ", '%Y-%m-%d')";
    }

    public String getCityOfBirth(){
        if (lob == null) return "NULL";
        String[] parts = lob.split(", ");
        if (parts.length == 1) return "NULL";
        return toSQLString(parts[0].replace("'", "\\'"));
    }

    public String getStateOfBirth(){
        if (lob == null) return "NULL";
        String[] parts = lob.split(", ");
        if (parts.length < 3) return "NULL";
        return toSQLString(parts[1].replace("'", "\\'"));
    }

    public String getCountryOfBirth(){
        if (lob == null) return "NULL";
        String[] parts = lob.split(", ");
        if (parts.length == 1) return toSQLString(parts[0].replace("'", "\\'"));
        if (parts.length == 2) return toSQLString(parts[1].replace("'", "\\'"));
        return toSQLString(parts[2].replace("'", "\\'"));
    }
}
