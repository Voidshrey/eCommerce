import axios from "axios";
import axiosClient from "./axiosClient";

export const userService = {
  findUserDetailsByUserName: async (username) => {
    const response = await axiosClient.get(`/user/userName`, {
      params: {
        userName: `${username}`,
      },
    });
    return response;
  },
};
