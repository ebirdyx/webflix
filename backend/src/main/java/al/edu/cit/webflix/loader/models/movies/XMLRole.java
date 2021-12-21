package al.edu.cit.webflix.loader.models.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static al.edu.cit.webflix.Utils.surroundWithSingleQuotes;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLRole {
    public XMLActor actor;

    public String character;

    public String getCharacter(){
        if (character == null) return "NULL";
        return surroundWithSingleQuotes(character);
    }
}
