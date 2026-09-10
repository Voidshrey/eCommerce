import React, { useState } from 'react';
import Navbar from './components/Navbar';
import ProductCard from './components/ProductCard';

export default function App() {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

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
        setError={setError}  />
    </div>
  );
}
