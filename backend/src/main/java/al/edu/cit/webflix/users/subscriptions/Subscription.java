package al.edu.cit.webflix.users.subscriptions;

import lombok.Data;

@Data
public class Subscription {
    private int id;

    private String name;

    private String code;

    private float cost;

    private int maxRentals;

    private int maxDuration;
}
