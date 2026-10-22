import React, { useEffect, useState } from "react";
import { productService } from "../api/productService";

const styles = {
  navbar: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
    padding: "10px 20px",
    backgroundColor: "#232f3e",
    color: "white",
  },
  logo: { fontSize: "24px", fontWeight: "bold" },
  searchContainer: { flex: 1, maxWidth: "500px", margin: "0 20px" },
  searchInput: {
    width: "100%",
    padding: "8px 12px",
    borderRadius: "4px",
    border: "none",
    fontSize: "16px",
    outline: "none",
  },
};

export default function NavBar({
  onSearchResults,
  setLoading,
  setError,
  fetchAllProducts,
}) {
  const [searchWord, setSearchWord] = useState("");

  useEffect(() => {
    if (!searchWord.trim()) {
      fetchAllProducts();
      return;
    }
    const delayDebounceFn = setTimeout(async () => {
      setLoading(true);
      setError(null);

      try {
        // Calling the imported API service cleanly
        const data = await productService.searchProduct(searchWord);
        onSearchResults(data);
      } catch (err) {
        setError(err.response?.data?.message || "Failed to fetch products");
      } finally {
        setLoading(false);
      }
    }, 300); // 300ms debounce delay

    return () => clearTimeout(delayDebounceFn);
  }, [searchWord, onSearchResults, setLoading, setError]);

  return (
    <nav style={styles.navbar}>
      <div style={styles.logo}>MyStore</div>
      <div style={styles.searchContainer}>
        <input
          type="text"
          placeholder="Search products by name..."
          value={searchWord}
          onChange={(e) => setSearchWord(e.target.value)}
          style={styles.searchInput}
        />
      </div>
    </nav>
  );
}
