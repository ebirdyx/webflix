import axios from "axios";

const url = "http://localhost:8080/api/v1/auth"

const UserApi = () => {
  return {
    login: (username: string, password: string) =>
      axios.post(`${url}/login`, {username, password}),
    logout: () => {},
    register: () => {}
  }
}

export default UserApi;
