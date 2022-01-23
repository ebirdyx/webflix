package al.edu.cit.webflix.users.subscriptions;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubscriptionRowMapper implements RowMapper<Subscription> {
    @Override
    public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        Subscription subscription = new Subscription();

        subscription.setId(rs.getInt("id"));
        subscription.setName(rs.getString("name"));
        subscription.setCode(rs.getString("code"));
        subscription.setCost(rs.getFloat("cost"));
        subscription.setMaxRentals(rs.getInt("max_rentals"));
        subscription.setMaxDuration(rs.getInt("max_duration"));

        return subscription;
    }
}
