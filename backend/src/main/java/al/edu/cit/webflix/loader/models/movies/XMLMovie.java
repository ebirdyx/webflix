package al.edu.cit.webflix.loader.models.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

import static al.edu.cit.webflix.Utils.toSQLString;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLMovie {
    @JacksonXmlProperty(isAttribute = true)
    public int id;

    public String title;

    public int year;

    @JsonProperty("country")
    public List<String> countries = new ArrayList<>();

    public String language;

    public int duration;

    public String synopsy;

    @JsonProperty("genre")
    public List<String> genres = new ArrayList<>();

    @JsonProperty("scriptwriter")
    public List<String> scriptwriters = new ArrayList<>();

    public XMLDirector director;

    @JsonProperty("role")
    public List<XMLRole> roles = new ArrayList<>();

    public String cover;

    @JsonProperty("trailer")
    public List<String> trailers = new ArrayList<>();


    public String getTitle() {
        return toSQLString(title.replace("'", "\\'"));
    }

    public String getEscapedSynopsy() {
        if (synopsy == null) return null;
        return toSQLString(synopsy.replace("'", "\\'"));
    }

    public XMLDirector getDirector() {
        if (director == null) return new XMLDirector();
        return director;
    }

    public String getEscapedCover() {
        if (cover == null) return null;
        return toSQLString(cover.replace("'", "\\'"));
    }
}
