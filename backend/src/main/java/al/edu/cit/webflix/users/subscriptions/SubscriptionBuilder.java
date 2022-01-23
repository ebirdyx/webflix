package al.edu.cit.webflix.users.subscriptions;

public class SubscriptionBuilder {
    private Subscription subscription = new Subscription();

    public SubscriptionBuilder setId(int id) {
        subscription.setId(id);
        return this;
    }

    public SubscriptionBuilder setName(String name) {
        subscription.setName(name);
        return this;
    }

    public SubscriptionBuilder setCode(String code) {
        subscription.setCode(code);
        return this;
    }

    public SubscriptionBuilder setCost(float cost) {
        subscription.setCost(cost);
        return this;
    }

    public SubscriptionBuilder setMaxRentals(int maxRentals) {
        subscription.setMaxRentals(maxRentals);
        return this;
    }

    public SubscriptionBuilder setMaxDuration(int maxDuration) {
        subscription.setMaxDuration(maxDuration);
        return this;
    }

    public Subscription build() {
        return subscription;
    }
}
