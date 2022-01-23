package al.edu.cit.webflix.users.customersubscriptions;

import al.edu.cit.webflix.users.subscriptions.Subscription;

import java.sql.Date;

public class CustomerSubscriptionBuilder {
    private CustomerSubscription customerSubscription = new CustomerSubscription();

    public CustomerSubscriptionBuilder setId(int id) {
        customerSubscription.setId(id);
        return this;
    }

    public CustomerSubscriptionBuilder setStartDate(Date startDate) {
        customerSubscription.setStartDate(startDate);
        return this;
    }

    public CustomerSubscriptionBuilder setEndDate(Date endDate) {
        customerSubscription.setEndDate(endDate);
        return this;
    }

    public CustomerSubscriptionBuilder setCustomerId(int customerId) {
        customerSubscription.setCustomerId(customerId);
        return this;
    }

    public CustomerSubscriptionBuilder setSubscription(Subscription subscription) {
        customerSubscription.setSubscription(subscription);
        return this;
    }

    public CustomerSubscription build() {
        return customerSubscription;
    }
}
