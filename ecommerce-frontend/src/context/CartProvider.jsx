import {
  createContext,
  useCallback,
  useContext,
  useEffect,
  useState,
} from "react";
import { cartService } from "../api/cartService";

const cartContext = createContext(undefined);

export const CartProvider = ({ children }) => {
  const [cartItems, setCartItems] = useState([]);
  const [isCartOpen, setIsCartOpen] = useState(false);

  const toggleCart = (open) => () => {
    setIsCartOpen(open);
  };

  const grandTotal = cartItems.reduce(
    (total, item) => total + item.price * item.quantity,
    0,
  );

  const cartItemsCount = cartItems.reduce(
    (total, item) => total + item.quantity,
    0,
  );

  const handleDecreaseQuantity = async (productId, currentQuantity) => {
    const userId = localStorage.getItem("userId");

    if (!userId) {
      return;
    }

    if (currentQuantity === 1) {
      try {
        await cartService.deleteCartItem(userId, productId);
        setCartItems((prevCartItems) => {
          return prevCartItems.filter((item) => item.productId !== productId);
        });
        return;
      } catch (error) {
        console.error("Error deleting cart item:", error);
        return;
      }
    }

    const newQuantity = currentQuantity - 1;

    try {
      var response = await cartService.updatecartItem(
        userId,
        productId,
        newQuantity,
      );
      if (response.status === 200) {
        setCartItems((prevCartItems) => {
          return prevCartItems.map((item) => {
            return item.productId === productId
              ? { ...item, quantity: newQuantity }
              : item;
          });
        });
      }
    } catch (error) {
      console.error("Error updating cart item:", error);
      return;
    }
  };

  const fetchCartItem = useCallback(async () => {
    const userId = localStorage.getItem("userId");

    if (!userId) {
      return;
    }

    const items = await cartService.getCartByUserId(userId);
    setCartItems(items.items || []);
  }, []);

  useEffect(() => {
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    if (!token || !userId) {
      return;
    }

    fetchCartItem().catch((error) => {
      console.error("Failed to load cart:", error);
    });
  }, [fetchCartItem]);

  const addToCart = async (productId, quantity = 1) => {
    const userId = localStorage.getItem("userId");

    if (!userId) {
      throw new Error("You must be logged in to add items to the cart.");
    }

    await cartService.addToCart(userId, productId, quantity);
    await fetchCartItem();
    setIsCartOpen(true);
  };

  return (
    <cartContext.Provider
      value={{
        toggleCart,
        addToCart,
        fetchCartItem,
        cartItems,
        isCartOpen,
        setCartItems,
        grandTotal,
        cartItemsCount,
        handleDecreaseQuantity,
      }}
    >
      {children}
    </cartContext.Provider>
  );
};

export const useCart = () => {
  const context = useContext(cartContext);

  if (!context) {
    throw new Error("useCart must be used within a CartProvider");
  }

  return context;
};
