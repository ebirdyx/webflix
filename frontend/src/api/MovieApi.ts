import axios from "axios";
import {Movie} from "./types";

const url = "http://localhost:8080"

const MovieApi = () => {
  return {
    getAll: () => axios.get<Movie[]>(`${url}/api/v1/movies`)
  }
}

export default MovieApi;