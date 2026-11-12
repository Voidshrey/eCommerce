import React from "react";
import { authService } from "../api/AuthService";
import { userService } from "../api/userService";
import { useState } from "react";

const userContext = React.createContext(undefined);

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState({
    userId: localStorage.getItem("userId") || null,
    userName: localStorage.getItem("userName") || "Guest",
    token: localStorage.getItem("token") || null,
    role: localStorage.getItem("role") || "ROLE_USER",
  });

  const [isAdmin, setIsAdmin] = useState(user?.role === "ROLE_ADMIN");

  const handleLogin = async (userName, password, e) => {
    e.preventDefault();
    if (
      ("" !== userName.trim() && "" !== password.trim()) ||
      (userName.length >= 0 && password.length >= 0)
    ) {
      let response = await authService.login(userName, password);
      if (
        response.status === 200 &&
        response.data?.responseDesc === "SUCCESS" &&
        response.data?.token.trim() !== ""
      ) {
        localStorage.setItem("token", response.data?.token);
        localStorage.setItem("role", response.data?.role);
        setIsAdmin(response.data?.role === "ROLE_ADMIN");
        await fetchUserDetailsByUserName(userName);
        setUser({
          userId: localStorage.getItem("userId"),
          userName: localStorage.getItem("userName"),
          token: localStorage.getItem("token"),
          role: localStorage.getItem("role"),
        });
      } else {
        throw new Error(
          "Username and password are not correct, please check and try again.",
        );
      }
    } else {
      throw new Error(
        "Username and password are null or empty, please check and try again.",
      );
    }
  };
  return (
    <userContext.Provider value={{ user, handleLogin, isAdmin }}>
      {children}
    </userContext.Provider>
  );
};

let fetchUserDetailsByUserName = async (userName) => {
  let response = await userService.findUserDetailsByUserName(userName);
  if (response.status === 200) {
    localStorage.setItem("userId", response.data?.id);
    localStorage.setItem("userName", response.data?.username);
  }
};

export const useUser = () => {
  const context = React.useContext(userContext);
  if (!context) {
    throw new Error("useUser must be used within a UserProvider");
  }

  return context;
};
