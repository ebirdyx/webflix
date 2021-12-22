package al.edu.cit.webflix.movies.genres;

public class MovieGenreBuilder {
    private final MovieGenre movieGenre = new MovieGenre();

    public MovieGenreBuilder setId(int id) {
        movieGenre.setId(id);
        return this;
    }

    public MovieGenreBuilder setGenreId(int genreId) {
        movieGenre.setGenreId(genreId);
        return this;
    }

    public MovieGenreBuilder setMovieId(int movieId){
        movieGenre.setMovieId(movieId);
        return this;
    }

    public MovieGenre build() {
        return movieGenre;
    }

}
