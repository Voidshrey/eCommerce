import axios from "axios";

// Create an axios instance with default configurations
const api = axios.create({
  baseURL: "http://localhost:8080/api/auth",
  headers: {
    "Content-Type": "application/json",
  },
});

export const authService = {
  login: async (username, password) => {
    const response = await api.post(`/login`, {
      username: username,
      password: password,
    });
    return response;
  },
  register: async (username, password, email) => {
    const response = await api.post("/register", {
      userName: username,
      password: password,
      email: email,
    });
    return response;
  },
};
