package al.edu.cit.webflix.loader.models.people;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class XMLPerson {
    @JacksonXmlProperty(isAttribute = true)
    public int id;

    public String name;

    public XMLBirth birth = new XMLBirth();

    public String photo;

    public String bio;
}
