package al.edu.cit.webflix.users.customersubscriptions;

import al.edu.cit.webflix.users.subscriptions.SubscriptionDao;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@AllArgsConstructor
public class CustomerSubscriptionRowMapper implements RowMapper<CustomerSubscription> {
    private SubscriptionDao subscriptionDao;

    @Override
    public CustomerSubscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        CustomerSubscription customerSubscription = new CustomerSubscription();

        customerSubscription.setId(rs.getInt("id"));
        customerSubscription.setStartDate(rs.getDate("start_date"));
        customerSubscription.setEndDate(rs.getDate("end_date"));
        customerSubscription.setCustomerId(rs.getInt("customer_id"));

        int subscriptionId = rs.getInt("subscription_id");
        customerSubscription
                .setSubscription(subscriptionDao.get(subscriptionId));

        return customerSubscription;
    }
}
