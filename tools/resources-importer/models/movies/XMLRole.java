package models.movies;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLRole {
    public XMLActor actor;

    public String character;

    public String toSQLString(String s){
        return "'" + s + "'";
    }

    public String getCharacter(){
        if (character == null) return "NULL";
        return toSQLString(character) + ", ";
    }
}
