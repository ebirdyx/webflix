package al.edu.cit.webflix.genres;

public class GenreBuilder {
    private final Genre genre = new Genre();

    public GenreBuilder setId(int id) {
        genre.setId(id);
        return this;
    }

    public GenreBuilder setName(String name) {
        genre.setName(name);
        return this;
    }

    public Genre build() {
        return genre;
    }
}
