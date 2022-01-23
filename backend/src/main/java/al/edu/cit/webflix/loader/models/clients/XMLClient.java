package al.edu.cit.webflix.loader.models.clients;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class XMLClient {
    public static int count = 2;

    public int index;

    public XMLClient() {
        index = count++;
    }

    @JacksonXmlProperty(isAttribute = true)
    public int id;

    @JsonProperty("lastname")
    public String lastName;

    @JsonProperty("firstname")
    public String firstName;

    public String email;

    public String phone;

    public String dob;

    public String address;

    public String city;

    public String province;

    @JsonProperty("postalcode")
    public String postalCode;

    @JsonProperty("creditcard")
    public XMLCreditCard creditCard;

    public String password;

    @JsonProperty("package")
    public String subscription;

    public int getCivicNo() {
        return Integer.parseInt(address.split(" ")[0]);
    }

    public String getStreet() {
        String[] parts = address.split(" ");
        String[] streetParts = Arrays.copyOfRange(parts, 1, parts.length);
        return String.join(" ", streetParts);
    }
}
