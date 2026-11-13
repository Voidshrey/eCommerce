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
  addProduct: async (productData) => {
    const response = await axiosClient.post("products", productData);
    return response.data;
  },
  updateProduct: async (productId, productData) => {
    const response = await axiosClient.put(
      `products/update/${productId}`,
      productData,
    );
    return response.data;
  },
};
