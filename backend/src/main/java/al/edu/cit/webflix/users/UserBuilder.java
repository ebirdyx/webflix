package al.edu.cit.webflix.users;

import al.edu.cit.webflix.configuration.PasswordEncoder;
import al.edu.cit.webflix.users.addresses.Address;
import al.edu.cit.webflix.users.creditcards.CreditCard;
import al.edu.cit.webflix.users.customersubscriptions.CustomerSubscription;
import lombok.AllArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
public class UserBuilder {
    private final User user = new User();

    public UserBuilder setId(int id) {
        user.setId(id);
        return this;
    }

    public UserBuilder setType(UserType type) {
        user.setUserType(type);
        return this;
    }

    public UserBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password) {
        user.setPasswordHash(password);
        return this;
    }

    public UserBuilder setFirstName(String firstName) {
        user.setFirstName(firstName);
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        user.setLastName(lastName);
        return this;
    }

    public UserBuilder setPhoneNumber(String phoneNumber) {
        user.setPhoneNumber(phoneNumber);
        return this;
    }

    public UserBuilder setAddress(Address address) {
        user.setAddress(address);
        return this;
    }

    public UserBuilder setDateOfBirth(Date dateOfBirth) {
        user.setBirthDate(dateOfBirth);
        return this;
    }

    public UserBuilder setCreditCard(CreditCard creditCard) {
        user.setCreditCard(creditCard);
        return this;
    }

    public UserBuilder setSubscription(CustomerSubscription customerSubscription) {
        user
                .getSubscriptions()
                .add(customerSubscription);

        return this;
    }

    public User build() {
        return user;
    }
}
