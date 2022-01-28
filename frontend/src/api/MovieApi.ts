import axios from "axios";
import {Movie} from "./types";

const url = "http://localhost:8080/api/v1/movies"

const MovieApi = () => {
  return {
    getAll: () => axios.get<Movie[]>(url),
    rent: (userId: number, movieId: number) =>
      axios.post(`${url}/rent`, {movieId: movieId, userId: userId})
  }
}

export default MovieApi;