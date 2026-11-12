import { useCart } from "../context/CartProvider";
import { useUser } from "../context/UserProvider";

export default function ProductCard({ products }) {
  const { addToCart: addItemToCart } = useCart();
  const { user } = useUser();

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
  const isAdmin = user?.role === "ROLE_ADMIN";
  return (
    <>
      <div style={styles.container}>
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
              {isAdmin ? (
                <button onClick={() => console.log("Edit product", product.id)}>
                  Update/Edit Product
                </button>
              ) : (
                <button onClick={() => addToCart(product?.id)}>
                  Add to cart
                </button>
              )}
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
