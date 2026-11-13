import { Box, Typography, TextField, Button } from "@mui/material";
import React, { useState } from "react";
import { productService } from "../api/productService";

const AdminProductForm = ({ onSuccess, onCancel }) => {
  const [productName, setProductName] = useState("");
  const [productDescription, setProductDescription] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [price, setPrice] = useState("");
  const [totalQuantity, setTotalQuantity] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async () => {
    setLoading(true);
    try {
      const payload = {
        name: productName,
        description: productDescription,
        imageUrl: imageUrl,
        price: parseFloat(price),
        totalQuantity: parseInt(totalQuantity),
      };

      await productService.addProduct(payload);
      setProductName("");
      setProductDescription("");
      setImageUrl("");
      setPrice("");
      setTotalQuantity("");
      onSuccess && onSuccess();
    } catch (error) {
      console.error("Error adding product:", error);
    } finally {
      setLoading(false);
    }
  };
  return (
    <>
      <Box
        flexDirection="column"
        component="form"
        sx={{
          display: "flex",
          flexDirection: "column",
          p: 3,
          gap: 2.5,
          backgroundColor: "#232f3e", // Matches your NavBar theme
          borderRadius: "8px",
        }}
        onSubmit={(e) => {
          e.preventDefault();
          handleSubmit();
        }}
      >
        <Box sx={rowStyle}>
          <Typography sx={labelStyle}>Product Name :</Typography>
          <TextField
            fullWidth
            size="small"
            variant="outlined"
            placeholder="product name..."
            value={productName}
            onChange={(e) => setProductName(e.target.value)}
            sx={inputStyle}
          />
        </Box>
        <Box sx={rowStyle}>
          <Typography sx={labelStyle}>Product Description :</Typography>

          <TextField
            fullWidth
            size="small"
            variant="outlined"
            placeholder="product description..."
            value={productDescription}
            onChange={(e) => setProductDescription(e.target.value)}
            sx={inputStyle}
          />
        </Box>
        <Box sx={rowStyle}>
          <Typography sx={labelStyle}>Image URL :</Typography>
          <TextField
            fullWidth
            size="small"
            variant="outlined"
            placeholder="image url..."
            value={imageUrl}
            onChange={(e) => setImageUrl(e.target.value)}
            sx={inputStyle}
          />
        </Box>
        <Box sx={rowStyle}>
          <Typography sx={labelStyle}>Price :</Typography>
          <TextField
            fullWidth
            size="small"
            variant="outlined"
            placeholder="price..."
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            sx={{
              borderRadius: "4px",
              backgroundColor: "white",
              "& .MuiOutlinedInput-notchedOutline": { border: "none" },
            }}
          />
        </Box>
        <Box sx={rowStyle}>
          <Typography sx={labelStyle}>Total Quantity :</Typography>
          <TextField
            fullWidth
            size="small"
            variant="outlined"
            placeholder="total quantity..."
            value={totalQuantity}
            onChange={(e) => setTotalQuantity(e.target.value)}
            sx={inputStyle}
          />
        </Box>
        <Box
          sx={{ display: "flex", justifyContent: "flex-end", gap: 2, mt: 1 }}
        >
          {onCancel && (
            <Button
              type="button"
              variant="outlined"
              onClick={onCancel}
              sx={{ color: "white", borderColor: "white" }}
            >
              Cancel
            </Button>
          )}
          <Button
            type="submit"
            variant="contained"
            disabled={loading}
            sx={{
              backgroundColor: "#ff9900",
              color: "#232f3e",
              fontWeight: "bold",
              "&:hover": { backgroundColor: "#e68a00" },
            }}
          >
            {loading ? "Adding..." : "Add Product"}
          </Button>
        </Box>
      </Box>
    </>
  );
};

const rowStyle = {
  display: "flex",
  alignItems: "center",
  gap: 2,
};

const inputStyle = {
  borderRadius: "4px",
  backgroundColor: "white",
  "& .MuiOutlinedInput-notchedOutline": { border: "none" },
};

const labelStyle = {
  minWidth: "150px",
  fontSize: "1.05rem",
  fontWeight: "bold",
  color: "#ff9900",
};
export default AdminProductForm;
