package al.edu.cit.webflix.users;

import al.edu.cit.webflix.users.addresses.Address;
import al.edu.cit.webflix.users.creditcards.CreditCard;
import al.edu.cit.webflix.users.customersubscriptions.CustomerSubscription;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private int id;

    private UserType userType;

    private String username;

    private String passwordHash;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Address address;

    private CreditCard creditCard;

    private List<CustomerSubscription> subscriptions = new ArrayList<>();
}
