import axios from "axios";
import {Movie} from "./types";

const url = "http://localhost:8080/api/v1/movies"

const MovieApi = () => {
  return {
    getAll: () => axios.get<Movie[]>(url)
  }
}

export default MovieApi;