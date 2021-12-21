package al.edu.cit.webflix.people;

import java.util.Date;

public class PersonBuilder {
    private final Person person = new Person();

    public PersonBuilder setId(int id) {
        person.setId(id);
        return this;
    }

    public PersonBuilder setName(String name) {
        person.setName(name);
        return this;
    }

    public PersonBuilder setDob(Date dob) {
        person.setDob(dob);
        return this;
    }

    public PersonBuilder setBio(String bio) {
        person.setBio(bio);
        return this;
    }

    public PersonBuilder setPhoto(String photo) {
        person.setPhoto(photo);
        return this;
    }

    public PersonBuilder setBirthCity(String birthCity) {
        person.setBirthCity(birthCity);
        return this;
    }

    public PersonBuilder setBirthState(String birthState) {
        person.setBirthState(birthState);
        return this;
    }

    public PersonBuilder setBirthCountry(String birthCountry) {
        person.setBirthCountry(birthCountry);
        return this;
    }

    public Person build() {
        return person;
    }
}
