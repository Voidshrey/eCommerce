import React from "react";
import { authService } from "../api/AuthService";
import { userService } from "../api/userService";
import { useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";

const userContext = React.createContext(undefined);

export const UserProvider = ({ children }) => {
  const navigate = useNavigate();
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

  const handleLogOut = async () => {
    let userId = localStorage.getItem("userId");
    let userName = localStorage.getItem("userName");
    let token = localStorage.getItem("token");
    let role = localStorage.getItem("role");
    if (userId && userName && token && role) {
      let response = await authService.logout(token);
      if (response.status === 200) {
        localStorage.clear();
        navigate("/login", { replace: true });
      }
    } else {
      localStorage.clear();
      navigate("/login", { replace: true });
    }
  };

  return (
    <userContext.Provider value={{ user, handleLogin, isAdmin, handleLogOut }}>
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
