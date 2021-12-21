package al.edu.cit.webflix.loader.models.people;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static al.edu.cit.webflix.common.Utils.surroundWithSingleQuotes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLBirth {
    public String dob;

    public String lob;

    public String getSQLDob(){
        if (dob == null) return null;
        return "STR_TO_DATE(" + surroundWithSingleQuotes(dob) + ", '%Y-%m-%d')";
    }

    public String getCityOfBirth(){
        if (lob == null) return null;
        String[] parts = lob.split(", ");
        if (parts.length == 1) return "NULL";
        return surroundWithSingleQuotes(parts[0]);
    }

    public String getStateOfBirth(){
        if (lob == null) return null;
        String[] parts = lob.split(", ");
        if (parts.length < 3) return null;
        return surroundWithSingleQuotes(parts[1]);
    }

    public String getCountryOfBirth(){
        if (lob == null) return null;
        String[] parts = lob.split(", ");
        if (parts.length == 1) return surroundWithSingleQuotes(parts[0]);
        if (parts.length == 2) return surroundWithSingleQuotes(parts[1]);
        return surroundWithSingleQuotes(parts[2]);
    }
}
