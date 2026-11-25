import { useState, useEffect } from "react";
import { Dialog, DialogTitle, DialogContent, Typography } from "@mui/material";
import AdminProductForm from "./AdminProductForm";

const AdminDashboard = ({
  initialData,
  openEditModal,
  openAddModal,
  onClose,
  onSuccess,
}) => {
  const [openModal, setOpenModal] = useState(false);
  const [selectedData, setSelectedData] = useState(null);
  const [editMode, setEditMode] = useState(false);

  const closeModal = () => {
    setOpenModal(false);
    onClose && onClose();
  };

  useEffect(() => {
    if (initialData) {
      setEditMode(true);
      setOpenModal(openEditModal);
      setSelectedData(initialData);
    }
  }, [initialData, openEditModal]);

  useEffect(() => {
    if (openAddModal) {
      setEditMode(false);
      setOpenModal(openAddModal);
      setSelectedData(null);
    }
  }, [openAddModal]);

  const handleProductAdded = () => {
    closeModal();
    onSuccess && onSuccess();
  };

  return (
    <Dialog open={openModal} onClose={closeModal}>
      <DialogTitle>
        {" "}
        <Typography
          variant="h5"
          sx={{
            fontWeight: "bold",
            color: "#ff9900",
            textAlign: "center",
            mb: -1,
          }}
        >
          {editMode ? "Edit Product" : "Add New Product"}{" "}
        </Typography>
      </DialogTitle>
      <DialogContent>
        <AdminProductForm
          onSuccess={handleProductAdded}
          onCancel={closeModal}
          initialData={selectedData}
        />
      </DialogContent>
    </Dialog>
  );
};

export default AdminDashboard;
