package al.edu.cit.webflix.movies.genres;

import al.edu.cit.webflix.genres.Genre;

public class MovieGenreBuilder {
    private final MovieGenre movieGenre = new MovieGenre();

    public MovieGenreBuilder setId(int id) {
        movieGenre.setId(id);
        return this;
    }

    public MovieGenreBuilder setGenre(Genre genre) {
        movieGenre.setGenre(genre);
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
