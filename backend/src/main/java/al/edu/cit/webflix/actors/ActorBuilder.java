package al.edu.cit.webflix.actors;

import al.edu.cit.webflix.people.Person;

public class ActorBuilder {
    private final Actor actor = new Actor();

    public ActorBuilder setId(int id) {
        actor.setId(id);
        return this;
    }

    public ActorBuilder setPerson(Person person) {
        actor.setPerson(person);
        return this;
    }

    public ActorBuilder setCharacterName(String characterName) {
        actor.setCharacterName(characterName);
        return this;
    }

    public ActorBuilder setMovieId(int movieId){
        actor.setMovieId(movieId);
        return this;
    }

    public Actor build() {
        return actor;
    }
}
