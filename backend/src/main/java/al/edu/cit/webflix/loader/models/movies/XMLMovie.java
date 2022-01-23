package al.edu.cit.webflix.loader.models.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

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

    public String synopsis;

    @JsonProperty("genre")
    public List<String> genres = new ArrayList<>();

    @JsonProperty("scriptwriter")
    public List<String> scriptwriters = new ArrayList<>();

    public XMLDirector director = new XMLDirector();

    @JsonProperty("role")
    public List<XMLRole> roles = new ArrayList<>();

    public String cover;

    @JsonProperty("trailer")
    public List<String> trailers = new ArrayList<>();
}
