package al.edu.cit.webflix.trailers;

public class TrailerBuilder {
    private Trailer trailer = new Trailer();

    public TrailerBuilder setId(int id){
        trailer.setId(id);
        return this;
    }

    public TrailerBuilder setLink(String link){
        trailer.setLink(link);
        return this;
    }

    public TrailerBuilder setMovieId(int movieId){
        trailer.setMovieId(movieId);
        return this;
    }

    public Trailer build(){
        return trailer;
    }
}
