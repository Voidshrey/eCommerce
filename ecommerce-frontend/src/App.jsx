import LoginModal from "./components/LoginModal";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";
import ProductList from "./components/ProductList";
import { CartProvider } from "./context/CartProvider";

export default function App() {
  return (
    <>
      <CartProvider>
        <BrowserRouter>
          <Routes>
            <Route path="/login" element={<LoginModal />} />
            <Route
              path="/products"
              element={
                <ProtectedRoute>
                  <ProductList />
                </ProtectedRoute>
              }
            />
            <Route
              path="*"
              element={
                <Navigate
                  to={localStorage.getItem("token") ? "/products" : "/login"}
                  replace
                />
              }
            />
          </Routes>
        </BrowserRouter>
      </CartProvider>
    </>
  );
}
