import React, { useEffect, useState } from "react";
import { cartService } from "../api/cartService";
import NavBar from "./Navbar";
import { productService } from "../api/productService";

export default function ProductCard() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [cart, setCart] = useState([]);

  const fetchAllProducts = async () => {
    let response = await productService.getAllProducts();
    setProducts(response);
  };

  useEffect(() => {
    fetchAllProducts();
  }, []);

  const addToCart = async (productId) => {
    // 1. Find the product details from your main list first
    const productToAdd = products.find((p) => p.id === productId);

    // Safety check: if the product doesn't exist, stop immediately
    if (!productToAdd) return;

    try {
      // 2. Call your API first (Outside of the state updater)
      const data = await cartService.addToCart(
        Number.parseInt(localStorage.getItem("userId")),
        productToAdd?.id,
        1,
      );

      if (data.status === 200) {
        // 3. If the API succeeds, safely update your React state
        setCart((prevCart) => {
          const itemExist = prevCart.find((item) => item.id === productId);

          if (itemExist) {
            // Increment the quantity of the existing item
            return prevCart.map((item) =>
              item.id === productId
                ? { ...item, quantity: (item.quantity || 1) + 1 }
                : item,
            );
          }

          // Add the new item to the cart array
          return [...prevCart, { ...productToAdd, quantity: 1 }];
        });
      }
    } catch (error) {
      console.error("Failed to add item to server cart:", error);
      // Optional: Add user-facing error message here (e.g., alert or toast notification)
    }
  };

  return (
    <>
      <div>
        <NavBar
          onSearchResults={setProducts}
          setLoading={setLoading}
          setError={setError}
          fetchAllProducts={fetchAllProducts}
        />
      </div>
      <div style={styles.container}>
        {!loading && <h2>Search Results</h2>}

        {loading && <p>Searching products...</p>}
        {error && <p style={{ color: "red" }}>Error: {error}</p>}

        {!loading && products.length === 0 && (
          <p>No products found. Start typing in the navbar to search!</p>
        )}

        <div style={styles.grid}>
          {products.map((product) => (
            <div key={product.id} style={styles.card}>
              <img
                src={product.imageUrl || "https://placeholder.com"}
                alt={product.name}
                style={styles.image}
              />
              <h3>{product.name}</h3>
              <p>{product.description}</p>
              <strong style={styles.price}>${product.price}</strong>
              <button onClick={() => addToCart(product?.id)}>
                Add to cart
              </button>
            </div>
          ))}
        </div>
      </div>
    </>
  );
}

const styles = {
  container: { padding: "20px", fontFamily: "Arial, sans-serif" },
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fill, minmax(200px, 1fr))",
    gap: "20px",
    marginTop: "20px",
  },
  card: {
    border: "1px solid #ddd",
    borderRadius: "8px",
    padding: "15px",
    textAlign: "center",
    boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
  },
  image: {
    width: "100%",
    height: "150px",
    objectFit: "cover",
    borderRadius: "4px",
  },
  price: {
    color: "#B12704",
    fontSize: "18px",
    display: "block",
    marginTop: "10px",
  },
};
