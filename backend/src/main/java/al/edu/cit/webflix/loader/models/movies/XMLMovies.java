package al.edu.cit.webflix.loader.models.movies;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class XMLMovies {

    @JsonProperty("movie")
    public List<XMLMovie> movies = new ArrayList<>();
}
