package al.edu.cit.webflix.scriptwriter;

public class ScriptwriterBuilder {
    private final Scriptwriter scriptwriter = new Scriptwriter();

    public ScriptwriterBuilder setId(int id) {
        scriptwriter.setId(id);
        return this;
    }

    public ScriptwriterBuilder setName(String name) {
        scriptwriter.setName(name);
        return this;
    }

    public ScriptwriterBuilder setMovieId(int movieId){
        scriptwriter.setMovieId(movieId);
        return this;
    }

    public Scriptwriter build() {
        return scriptwriter;
    }
}

