import React from 'react'

export default function ProductCard({products , loading , error}) {
  return (
    <>
    <div style={styles.container}>
       {!loading  && <h2>Search Results</h2>}
        
        {loading && <p>Searching products...</p>}
        {error && <p style={{ color: 'red' }}>Error: {error}</p>}
        
        {!loading && products.length === 0 && (
          <p>No products found. Start typing in the navbar to search!</p>
        )}

        <div style={styles.grid}>
          {products.map((product) => (
            <div key={product.id} style={styles.card}>
              <img 
                src={product.imageUrl || 'https://placeholder.com'} 
                alt={product.name} 
                style={styles.image} 
              />
              <h3>{product.name}</h3>
              <p>{product.description}</p>
              <strong style={styles.price}>${product.price}</strong>
            </div>
          ))}
        </div>
      </div>
    </>
  )
}

const styles = {
  container: { padding: '20px', fontFamily: 'Arial, sans-serif' },
  grid: { display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(200px, 1fr))', gap: '20px', marginTop: '20px' },
  card: { border: '1px solid #ddd', borderRadius: '8px', padding: '15px', textAlign: 'center', boxShadow: '0 2px 5px rgba(0,0,0,0.1)' },
  image: { width: '100%', height: '150px', objectFit: 'cover', borderRadius: '4px' },
  price: { color: '#B12704', fontSize: '18px', display: 'block', marginTop: '10px' },
};
