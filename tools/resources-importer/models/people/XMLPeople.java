package models.people;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class XMLPeople {
    @JsonProperty("person")
    public List<XMLPerson> people = new ArrayList<>();
}
