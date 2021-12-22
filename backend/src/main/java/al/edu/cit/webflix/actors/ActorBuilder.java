package al.edu.cit.webflix.actors;

public class ActorBuilder {
    private final Actor actor = new Actor();

    public ActorBuilder setId(int id) {
        actor.setId(id);
        return this;
    }

    public ActorBuilder setPersonId(int personId) {
        actor.setPersonId(personId);
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
