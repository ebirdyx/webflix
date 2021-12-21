package al.edu.cit.webflix.movies;

public class MovieBuilder {
    private Movie movie = new Movie();

    public MovieBuilder setId(int id) {
        movie.setId(id);
        return this;
    }

    public MovieBuilder setTitle(String title) {
        movie.setTitle(title);
        return this;
    }

    public MovieBuilder setPublishingYear(int publishingYear) {
        movie.setPublishingYear(publishingYear);
        return this;
    }

    public MovieBuilder setDuration(int duration) {
        movie.setDuration(duration);
        return this;
    }

    public MovieBuilder setSynopsys(String synopsys) {
        movie.setSynopsys(synopsys);
        return this;
    }

    public MovieBuilder setCover(String cover) {
        movie.setCover(cover);
        return this;
    }

    public MovieBuilder setLanguageId(int languageId) {
        movie.setLanguageId(languageId);
        return this;
    }

    public MovieBuilder setDirectorId(int directorId) {
        movie.setDirectorId(directorId);
        return this;
    }

    public Movie build() {
        return movie;
    }
}
