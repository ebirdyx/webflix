package al.edu.cit.webflix.loader.models.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLRole {
    public XMLActor actor;

    public String character;
}
