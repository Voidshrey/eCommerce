import {
  Drawer,
  Box,
  Typography,
  List,
  ListItem,
  ListItemText,
  Divider,
  Button,
  IconButton,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import RemoveIcon from "@mui/icons-material/Remove";
import { useCart } from "../context/CartProvider";

function CartDrawer() {
  const {
    isCartOpen,
    toggleCart,
    cartItems,
    grandTotal,
    handleDecreaseQuantity,
    addToCart: handleAddToCart,
  } = useCart();

  return (
    <Drawer anchor="right" open={isCartOpen} onClose={toggleCart(false)}>
      <Box
        sx={{
          width: 350,
          padding: 3,
          display: "flex",
          flexDirection: "column",
          height: "100%",
        }}
      >
        <Typography variant="h5" sx={{ marginBottom: 2, fontWeight: "bold" }}>
          Your Shopping Cart
        </Typography>
        <Divider />

        {/* 1. Scrollable list block of cart items */}
        <Box sx={{ flexGrow: 1, overflowY: "auto", marginTop: 2 }}>
          {cartItems.length === 0 ? (
            <Typography
              variant="body1"
              sx={{
                color: "text.secondary",
                textAlign: "center",
                marginTop: 4,
              }}
            >
              Your cart is empty.
            </Typography>
          ) : (
            <List>
              {cartItems.map((item) => (
                <ListItem
                  key={item.id}
                  divider
                  sx={{
                    paddingX: 0,
                    justifyContent: "space-between",
                    alignItems: "center",
                  }}
                >
                  <ListItemText
                    primary={item.productName}
                    secondary={`Qty: ${item.quantity} × ₹${item.price.toFixed(2)}`}
                  />
                  <Box sx={{ display: "flex", gap: 1, alignItems: "center" }}>
                    <IconButton
                      onClick={() =>
                        handleDecreaseQuantity(item.productId, item.quantity)
                      }
                      aria-label="remove item"
                      color="error"
                      size="small"
                    >
                      <RemoveIcon fontSize="small" />
                    </IconButton>
                    <Typography variant="body2" sx={{ fontWeight: "medium" }}>
                      ₹{(item.price * item.quantity).toFixed(2)}
                    </Typography>
                    <IconButton
                      onClick={() =>
                        handleAddToCart(item.productId, item.quantity)
                      }
                      aria-label="add item"
                      color="success"
                      size="small"
                    >
                      <AddIcon fontSize="small" />
                    </IconButton>
                  </Box>
                </ListItem>
              ))}
            </List>
          )}
        </Box>

        {/* 2. Persistent bottom panel showing the Grand Total */}
        <Box sx={{ marginTop: "auto", paddingTop: 2 }}>
          <Divider sx={{ marginBottom: 2 }} />
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
              marginBottom: 3,
            }}
          >
            <Typography variant="h6" sx={{ fontWeight: "bold" }}>
              Grand Total:
            </Typography>
            <Typography
              variant="h6"
              sx={{ fontWeight: "bold", color: "#2e7d32" }}
            >
              ₹{grandTotal.toFixed(2)}
            </Typography>
          </Box>

          <Button
            variant="contained"
            color="primary"
            fullWidth
            size="large"
            disabled={cartItems.length === 0}
          >
            Proceed to Checkout
          </Button>
        </Box>
      </Box>
    </Drawer>
  );
}

export default CartDrawer;
