package al.edu.cit.webflix.loader.models.clients;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class XMLClients {
    @JsonProperty("customer")
    public List<XMLClient> clients = new ArrayList<>();
}
