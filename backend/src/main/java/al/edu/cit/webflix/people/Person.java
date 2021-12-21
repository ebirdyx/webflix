package al.edu.cit.webflix.people;

import lombok.Data;

import java.util.Date;

@Data
public class Person {
    private int id;

    private String name;

    private Date dob;

    private String bio;

    private String photo;

    private String birthCity;

    private String birthState;

    private String birthCountry;
}
