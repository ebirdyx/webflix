package al.edu.cit.webflix.countries;

public class CountryBuilder {
    private final Country country = new Country();

    public CountryBuilder setId(int id) {
        country.setId(id);
        return this;
    }

    public CountryBuilder setName(String name) {
        country.setName(name);
        return this;
    }

    public Country build() {
        return country;
    }
}
