import { Navigate } from "react-router-dom";

// This component acts as a gatekeeper for private pages
const ProtectedRoute = ({ children }) => {
  const token = localStorage.getItem("token");
  const userId = localStorage.getItem("userId");

  if (!token || !userId) {
    return <Navigate to="/login" replace></Navigate>;
  }
  // If the token is there, let them view the page normally
  return children;
};

export default ProtectedRoute;
