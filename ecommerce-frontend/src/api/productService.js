import axiosClient from "./axiosClient";

// Create an axios instance with default configurations
// const api = axiosClient.create({
//   headers: {
//     "Content-Type": "application/json",
//     Authorization: `Bearer ${localStorage.getItem("token")}`,
//   },
// });

export const productService = {
  searchProduct: async (query) => {
    if (!query.trim()) return [];
    const response = await axiosClient.get("products", {
      params: { searchName: query },
    });
    return response.data;
  },
  getAllProducts: async () => {
    const response = await axiosClient.get("products/getAll");
    return response.data;
  },
};
