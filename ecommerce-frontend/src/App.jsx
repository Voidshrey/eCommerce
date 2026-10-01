import React, { useState } from 'react';
import Navbar from './components/Navbar';
import ProductCard from './components/ProductCard';
import { cartService } from './api/cartService';

export default function App() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [cart , setCart] = useState([]);

const addToCart = async (productId) => {
  // 1. Find the product details from your main list first
  const productToAdd = products.find(p => p.id === productId);
  
  // Safety check: if the product doesn't exist, stop immediately
  if (!productToAdd) return; 

  try {
    // 2. Call your API first (Outside of the state updater)
    const data = await cartService.addToCart(1, productToAdd?.id, 1);

    // 3. If the API succeeds, safely update your React state
    setCart(prevCart => {
      const itemExist = prevCart.find(item => item.id === productId);

      if (itemExist) {
        // Increment the quantity of the existing item
        return prevCart.map(item => 
          item.id === productId 
            ? { ...item, quantity: (item.quantity || 1) + 1 } 
            : item
        );
      } 

      // Add the new item to the cart array
      return [...prevCart, { ...productToAdd, quantity: 1 }];
    });

  } catch (error) {
    console.error("Failed to add item to server cart:", error);
    // Optional: Add user-facing error message here (e.g., alert or toast notification)
  }
};


// let await addToCartFunction() =>{
// };



  return (
    <div>
      {/* Navbar directly handles the fetch and updates App's states */}
      <Navbar 
        onSearchResults={setProducts} 
        setLoading={setLoading} 
        setError={setError} 
      />
      <ProductCard 
      products={products}
       setLoading={setLoading} 
       addToCart={addToCart}
        setError={setError}  />
    </div>
  );
}
