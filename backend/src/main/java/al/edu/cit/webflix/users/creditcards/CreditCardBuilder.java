package al.edu.cit.webflix.users.creditcards;

public class CreditCardBuilder {
    private CreditCard creditCard = new CreditCard();

    public CreditCardBuilder setId(int id) {
        creditCard.setId(id);
        return this;
    }

    public CreditCardBuilder setNumber(String number) {
        creditCard.setNumber(number);
        return this;
    }

    public CreditCardBuilder setType(CreditCardType type) {
        creditCard.setType(type);
        return this;
    }

    public CreditCardBuilder setExpirationMonth(int month) {
        creditCard.setExpirationMonth(month);
        return this;
    }

    public CreditCardBuilder setExpirationYear(int year) {
        creditCard.setExpirationYear(year);
        return this;
    }

    public CreditCardBuilder setCvv(String cvv) {
        creditCard.setCvv(cvv);
        return this;
    }

    public CreditCard build() {
        return creditCard;
    }
}
