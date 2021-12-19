package models.movies;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class XMLDirector {
    @JacksonXmlProperty(isAttribute = true)
    public int id;

    @JacksonXmlText
    public String name;

}
