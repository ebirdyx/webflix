package al.edu.cit.webflix.loader.models.people;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import static al.edu.cit.webflix.loader.models.Utils.toSQLString;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLPerson {
    @JacksonXmlProperty(isAttribute = true)
    public int id;

    public String name;

    public XMLBirth birth;

    public String photo;

    public String bio;

    public String getPhoto() {
        if (photo == null) return "NULL";
        return toSQLString(photo.replace("'", "\\'"));
    }

    public String getName() {
        return toSQLString(name.replace("'", "\\'"));
    }

    public XMLBirth getBirth() {
        if (birth == null) return new XMLBirth();
        return birth;
    }

    public String getEscapedBio() {
        if (bio == null) return null;
        return toSQLString(bio.replace("'", "\\'"));
    }
}
