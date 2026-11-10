import { useEffect, useState } from "react";
import NavBar from "./Navbar";
import { productService } from "../api/productService";
import ProductCard from "./ProductCard";
import CartDrawer from "./CartDrawer";

export default function ProductList() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchAllProducts = async () => {
    try {
      setLoading(true);
      let response = await productService.getAllProducts();
      setProducts(response);
    } catch (error) {
      setError(error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAllProducts();
  }, []);

  return (
    <>
      <div>
        <NavBar
          onSearchResults={setProducts}
          setLoading={setLoading}
          setError={setError}
          fetchAllProducts={fetchAllProducts}
        />
        <CartDrawer />
      </div>
      <div style={styles.container}>
        {loading && <p>Searching products...</p>}
        {error && <p style={{ color: "red" }}>Error: {error}</p>}

        {!loading && products.length === 0 && (
          <p>No products found. Start typing in the navbar to search!</p>
        )}

        <ProductCard products={products} />
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
