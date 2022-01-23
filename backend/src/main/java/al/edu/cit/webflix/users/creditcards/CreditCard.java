package al.edu.cit.webflix.users.creditcards;

import lombok.Data;

@Data
public class CreditCard {
    private int id;

    private String number;

    private CreditCardType type;

    private int expirationMonth;

    private int expirationYear;

    private String cvv;
}
