package al.edu.cit.webflix.movies;

import lombok.Data;

@Data
public class Movie {
    private int id;

    private String title;

    private int publishingYear;

    private int duration;

    private String synopsys;

    private String cover;

    private int languageId;

    private int directorId;
}
