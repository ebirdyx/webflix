import React, {useEffect, useState} from "react";
import {Container, Row} from "react-bootstrap";
import MovieApi from "../api/MovieApi";
import {Movie} from "../api/types";
import MovieCard from "./MovieCard";

const Home = () => {
  const [movies, setMovies] = useState<Movie[]>([]);

  const getMovies = () => {
    MovieApi()
      .getAll()
      .then(res => setMovies(res.data));
  }

  useEffect(() => {
    getMovies();
  }, []);

  if (movies.length === 0)
    return <div></div>;

  return (
    <Container>
      <Row md={4} className="g-4">
        {movies.map(movie =>
          <MovieCard movie={movie} />
        )}
      </Row>
    </Container>
  )
}

export default Home;