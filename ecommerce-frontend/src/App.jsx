import ProductCard from "./components/ProductCard";
import LoginModal from "./components/LoginModal";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";

export default function App() {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginModal />} />
          <Route
            path="/products"
            element={
              <ProtectedRoute>
                <ProductCard />
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
    </>
  );
}
