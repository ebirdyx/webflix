package al.edu.cit.webflix.users.addresses;

import lombok.Data;

@Data
public class Address {
    private int id;

    private int civicNumber;

    private String street;

    private String city;

    private String province;

    private String postalCode;
}
