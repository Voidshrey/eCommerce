import { useCart } from "../context/CartProvider";
import { useUser } from "../context/UserProvider";
import AdminDashboard from "./AdminDashboard";
import { useState } from "react";
import { Box } from "@mui/material";

export default function ProductCard({ products, fetchAllProducts }) {
  const { addToCart: addItemToCart } = useCart();
  const { user } = useUser();
  const [openEditModal, setOpenEditModal] = useState(false);
  const [selectedProduct, setSelectedProduct] = useState(null);

  const addToCart = async (productId) => {
    // 1. Find the product details from your main list first
    const productToAdd = products.find((p) => p.id === productId);

    // Safety check: if the product doesn't exist, stop immediately
    if (!productToAdd) return;

    try {
      await addItemToCart(productToAdd.id);
    } catch (error) {
      console.error("Failed to add item to cart:", error);
      // Optional: Add user-facing error message here (e.g., alert or toast notification)
    }
  };

  const handleEditProduct = (productId, product) => {
    setSelectedProduct({
      id: productId,
      ...product,
    });
    setOpenEditModal(true);
  };

  const handleProductEdited = () => {
    setOpenEditModal(false);
    fetchAllProducts();
  };

  const isAdmin = user?.role === "ROLE_ADMIN";
  return (
    <>
      <Box
        sx={{
          display: "grid",
          // Automatically fits as many 260px cards side-by-side as the screen allows!
          gridTemplateColumns: "repeat(auto-fill, minmax(260px, 1fr))",
          gap: "45px",
          marginTop: "20px",
          paddingX: 3,
        }}
      >
        {products.map((product) => (
          <div style={styles.card}>
            <img
              src={product.imageUrl || "https://placeholder.com"}
              alt={product.name}
              style={styles.image}
            />
            <div style={styles.cardBody}>
              <h3 style={styles.title}>{product.name}</h3>
              <p style={styles.description}>{product.description}</p>

              <div style={styles.footer}>
                {isAdmin && (
                  <p>
                    Quantity:{" "}
                    <span style={styles.quantity}>
                      {product.totalQuantity ? product.totalQuantity : "N/A"}
                    </span>
                  </p>
                )}
                <strong style={styles.price}>${product.price}</strong>
                {isAdmin ? (
                  <button
                    style={styles.updateButton}
                    onClick={() => handleEditProduct(product.id, product)}
                  >
                    Update Product
                  </button>
                ) : (
                  <button
                    style={styles.addToCartButton}
                    onClick={() => addToCart(product?.id)}
                  >
                    Add to cart
                  </button>
                )}
              </div>
            </div>
          </div>
        ))}
      </Box>
      <AdminDashboard
        initialData={selectedProduct}
        openEditModal={openEditModal}
        openAddModal={false}
        onClose={() => {
          setOpenEditModal(false);
          setSelectedProduct(null);
        }}
        onSuccess={handleProductEdited}
      />
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
    width: "100%",
    boxShadow: "0 2px 5px rgba(0,0,0,0.1)",
  },
  title: {
    margin: "0 0 8px 0",
    fontSize: "1.1rem",
    whiteSpace: "nowrap",
    overflow: "hidden",
    textOverflow: "ellipsis", // Keeps long product titles on 1 clean line
  },
  footer: {
    marginTop: "auto", // Automatically pushes price + button to the very bottom
    display: "flex",
    flexDirection: "column",
    gap: "8px",
  },
  cardBody: {
    display: "flex",
    flexDirection: "column",
    flexGrow: 1, // Expands body to fill remaining card height
    padding: "16px",
  },
  image: {
    width: "100%",
    height: "200px",
    objectFit: "cover",
    borderRadius: "4px",
  },
  price: {
    color: "#B12704",
    fontSize: "18px",
    display: "block",
    marginTop: "10px",
  },
  description: {
    fontSize: "14px",
    color: "#555",
    margin: "0 0 12px 0",
    display: "-webkit-box",
    WebkitLineClamp: 2,
    WebkitBoxOrient: "vertical",
    overflow: "hidden",
  },
  quantity: {
    fontSize: "14px",
    color: "#16ce16",
    marginTop: "5px",
  },
  updateButton: {
    backgroundColor: "#ff9900",
    color: "#fff",
    border: "none",
    padding: "10px",
    borderRadius: "4px",
    cursor: "pointer",
  },
  addToCartButton: {
    backgroundColor: "#232f3e",
    color: "#fff",
    border: "none",
    padding: "10px 15px",
    borderRadius: "4px",
    cursor: "pointer",
  },
};
