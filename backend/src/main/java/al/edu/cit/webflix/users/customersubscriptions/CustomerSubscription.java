package al.edu.cit.webflix.users.customersubscriptions;

import al.edu.cit.webflix.users.subscriptions.Subscription;
import lombok.Data;

import java.sql.Date;

@Data
public class CustomerSubscription {
    private int id;

    private Date startDate;

    private Date endDate;

    private int customerId;

    private Subscription subscription;
}
