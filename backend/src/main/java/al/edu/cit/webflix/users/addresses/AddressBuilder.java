package al.edu.cit.webflix.users.addresses;

public class AddressBuilder {
    private Address address = new Address();

    public AddressBuilder setId(int id) {
        address.setId(id);
        return this;
    }

    public AddressBuilder setCivicNumber(int civicNo) {
        address.setCivicNumber(civicNo);
        return this;
    }

    public AddressBuilder setStreet(String street) {
        address.setStreet(street);
        return this;
    }

    public AddressBuilder setCity(String city) {
        address.setCity(city);
        return this;
    }

    public AddressBuilder setProvince(String province) {
        address.setProvince(province);
        return this;
    }

    public AddressBuilder setPostalCode(String postalCode) {
        address.setPostalCode(postalCode);
        return this;
    }

    public Address build() {
        return address;
    }
}
