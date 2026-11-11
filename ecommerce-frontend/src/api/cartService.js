import axios from "axios";
import axiosClient from "./axiosClient";

export const cartService = {
  addToCart: async (userId, productId, quantity) => {
    const response = await axiosClient.post(
      `/cart/add/user/${userId}/product/${productId}?quantity=${quantity}`,
      {},
    );
    return response.data;
  },
  getCartByUserId: async (userId) => {
    const response = await axiosClient.get(`/cart/user/${userId}`);
    return response.data;
  },
  updatecartItem: async (userId, productId, quantity) => {
    const response = await axiosClient.put(
      `/cart/add/cart/user/${Number.parseInt(userId)}/product/${productId}?quantity=${quantity}`,
      {},
    );
    return response;
  },
  deleteCartItem: async (userId, productId) => {
    const response = await axiosClient.delete(
      `/cart/delete/user/${Number.parseInt(userId)}/product/${productId}`,
    );
    return response.data;
  },
};
