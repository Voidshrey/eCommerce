import React, { useEffect, useState } from "react";
import { productService } from "../api/productService";
import {
  AppBar,
  Toolbar,
  Typography,
  Box,
  TextField,
  IconButton,
  Badge,
} from "@mui/material";
import ShoppingCartIcon from "@mui/icons-material/ShoppingCart";
import { useCart } from "../context/CartProvider";

export default function NavBar({
  onSearchResults,
  setLoading,
  setError,
  fetchAllProducts,
}) {
  const { toggleCart, cartItemsCount } = useCart();
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
    <AppBar position="sticky" sx={{ backgroundColor: "#232f3e" }}>
      <Toolbar sx={{ justifyContent: "space-between" }}>
        <Typography
          variant="h6"
          component="div"
          sx={{ fontWeight: "bold", cursor: "pointer" }}
        >
          MyStore
        </Typography>
        <Box
          sx={{
            justifyContent: "space-between",
            display: "flex",
            alignItems: "flex-end",
            width: "30%",
          }}
        >
          <TextField
            fullWidth
            size="small"
            variant="outlined"
            placeholder="Search products by name..."
            value={searchWord}
            onChange={(e) => setSearchWord(e.target.value)}
            sx={{
              borderRadius: "4px",
              backgroundColor: "white",
              "& .MuiOutlinedInput-notchedOutline": { border: "none" },
            }}
          />

          {/* 7. Integration of your customized shopping cart button */}
          <IconButton
            color="inherit"
            onClick={toggleCart(true)}
            sx={{
              "&:focus-visible": {
                outline: "2px solid #ffcc00",
                outlineOffset: "2px",
              },
            }}
          >
            <Badge
              badgeContent={cartItemsCount}
              sx={{
                "& .MuiBadge-badge": {
                  backgroundColor: "#e53935",
                  color: "#fff",
                  fontWeight: "bold",
                  fontSize: "0.75rem",
                },
              }}
            >
              <ShoppingCartIcon sx={{ color: "#ff9900", fontSize: "1.5rem" }} />
            </Badge>
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
  );
}
