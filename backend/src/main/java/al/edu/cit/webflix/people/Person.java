package al.edu.cit.webflix.people;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
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
