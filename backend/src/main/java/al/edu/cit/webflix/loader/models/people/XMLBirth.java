package al.edu.cit.webflix.loader.models.people;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

import static al.edu.cit.webflix.common.Utils.stringToDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLBirth {
    public String dob;

    public String lob;

    public Date getDob() {
        if (dob == null) return null;
        String[] parts = dob.split("-");
        return stringToDate(parts[0], parts[1], parts[2]);
    }

    public String getCityOfBirth(){
        if (lob == null) return null;
        String[] parts = lob.split(", ");
        if (parts.length == 1) return null;
        return parts[0];
    }

    public String getStateOfBirth(){
        if (lob == null) return null;
        String[] parts = lob.split(", ");
        if (parts.length < 3) return null;
        return parts[1];
    }

    public String getCountryOfBirth(){
        if (lob == null) return null;
        String[] parts = lob.split(", ");
        if (parts.length == 1) return parts[0];
        if (parts.length == 2) return parts[1];
        return parts[2];
    }
}
