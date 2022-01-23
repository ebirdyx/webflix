package al.edu.cit.webflix.users;

import al.edu.cit.webflix.users.addresses.AddressDao;
import al.edu.cit.webflix.users.creditcards.CreditCardDao;
import al.edu.cit.webflix.users.customersubscriptions.CustomerSubscriptionDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class UserRowMapper implements RowMapper<User> {

    private AddressDao addressDao;
    private CreditCardDao creditCardDao;
    private CustomerSubscriptionDao customerSubscriptionDao;

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setUserType(UserType.valueOf(rs.getString("user_type")));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPhoneNumber(rs.getString("phone_no"));

        int addressId = rs.getInt("address_id");
        user.setAddress(addressDao.get(addressId));

        int creditCardId = rs.getInt("credit_card_id");
        user.setCreditCard(creditCardDao.get(creditCardId));

        user.setSubscriptions(
                customerSubscriptionDao.getByUserId(user.getId()));

        return user;
    }
}
