import LoginModal from "./components/LoginModal";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";
import ProductList from "./components/ProductList";
import { CartProvider } from "./context/CartProvider";
import { UserProvider } from "./context/UserProvider";

export default function App() {
  return (
    <>
      <BrowserRouter>
        <UserProvider>
          <CartProvider>
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
          </CartProvider>
        </UserProvider>
      </BrowserRouter>
    </>
  );
}
