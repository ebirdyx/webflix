package al.edu.cit.webflix.loader.models.clients;

import com.fasterxml.jackson.annotation.JsonProperty;

public class XMLCreditCard {
    @JsonProperty("cardtype")
    public String cardType;

    @JsonProperty("cardnumber")
    public String cardNumber;

    @JsonProperty("expiration-month")
    public int expirationMonth;

    @JsonProperty("expiration-year")
    public int expirationYear;
}
