import {
  Button,
  Container,
  Grid,
  Typography,
  TextField,
  Paper,
  CssBaseline,
} from "@mui/material";
import Snackbar from "@mui/material/Snackbar";
import SnackbarContent from "@mui/material/SnackbarContent";
import { useState } from "react";
import SignUpModal from "./SignUpModal";
import { useNavigate } from "react-router-dom";
import { useUser } from "../context/UserProvider";
import { useCart } from "../context/CartProvider";

function LoginModal() {
  const [loginUserName, setLoginUserName] = useState("");
  const [password, setPassword] = useState("");
  const [valueValidationFailed, setValueValidationFailed] = useState(false);
  const [validLogin, setValidLogin] = useState(false);
  const [openSignUp, setOpenSignUp] = useState(false);
  const [validationMessage, setValidationMessage] = useState("");

  const { user, handleLogin } = useUser();
  const { fetchCartItem } = useCart();

  let navigate = useNavigate();

  const handleCloseSignup = () => {
    setOpenSignUp(false);
  };

  let validateLogin = async (e) => {
    try {
      await handleLogin(loginUserName, password, e);
      await fetchCartItem();
      const token = user?.token || localStorage.getItem("token");
      const userId = user?.userId || localStorage.getItem("userId");
      const userName = user?.userName || localStorage.getItem("userName");
      if (token && userId && userName) {
        setLoginUserName("");
        setPassword("");
        setValidationMessage("successfull Login !!!");
        setValidLogin(true);
        setTimeout(() => {
          navigate("/products");
        }, 500);
      } else {
        setValidationMessage(
          "Username or password is null or empty please check",
        );
        setLoginUserName("");
        setPassword("");
        setValueValidationFailed(true);
      }
    } catch (error) {
      console.log(error);
      setValidationMessage(error?.message);
      setLoginUserName("");
      setPassword("");
      setValueValidationFailed(true);
    }
  };

  let handleOpenSignUp = (e) => {
    e.preventDefault();
    setOpenSignUp(true);
  };

  // let fetchUserDetailsByUserName = async (userName) => {
  //   let response = await userService.findUserDetailsByUserName(userName);
  //   if (response.status === 200) {
  //     localStorage.setItem("userId", response.data?.id);
  //     localStorage.setItem("userName", response.data?.username);
  //   }
  // };

  // let handleLogin = async (e) => {
  //   e.preventDefault();
  //   if (
  //     ("" !== userName.trim() && "" !== password.trim()) ||
  //     (userName.length >= 0 && password.length >= 0)
  //   ) {
  //     try {
  //       let response = await authService.login(userName, password);
  //       if (
  //         response.status === 200 &&
  //         response.data?.responseDesc === "SUCCESS" &&
  //         response.data?.token.trim() !== ""
  //       ) {
  //         setLoginToken(response.data?.token);
  //         localStorage.setItem("token", response.data?.token);
  //         await fetchUserDetailsByUserName(userName);
  //         await fetchCartItem();
  //         setUserName("");
  //         setPassword("");
  //         setValidationMessage("successfull Login !!!");
  //         setValidLogin(true);
  //         setTimeout(() => {
  //           navigate("/products");
  //         }, 500);
  //       } else {
  //         setValidationMessage(response.data?.responseDesc);
  //         setUserName("");
  //         setPassword("");
  //         setValueValidationFailed(true);
  //       }
  //     } catch (error) {
  //       console.log(error);
  //       setValidationMessage(error?.message);
  //       setUserName("");
  //       setPassword("");
  //       setValueValidationFailed(true);
  //     }
  //   } else {
  //     setValidationMessage(
  //       "Username or password is null or empty please check",
  //     );
  //     setUserName("");
  //     setPassword("");
  //     setValueValidationFailed(true);
  //   }
  // };

  let handleClose = (event, reason) => {
    if (reason === "clickaway") {
      setValueValidationFailed(false);
      return;
    }
    setValueValidationFailed(false);
  };

  return (
    <>
      <SignUpModal open={openSignUp} handleCloseSignup={handleCloseSignup} />
      <Snackbar
        open={valueValidationFailed}
        autoHideDuration={5000}
        onClose={handleClose}
        sx={{ color: "red" }}
      >
        {validLogin ? (
          <SnackbarContent
            sx={{ backgroundColor: "green", color: "black" }}
            message={validationMessage}
          />
        ) : (
          <SnackbarContent
            sx={{ backgroundColor: "red", color: "white" }}
            message={validationMessage}
          />
        )}
      </Snackbar>
      <CssBaseline />
      <Container maxWidth="xs" sx={{ marginTop: "20vh" }}>
        <Paper elevation={3} style={{ padding: "20px" }}>
          <form>
            <Grid container direction="column" spacing={3}>
              <Grid container alignItems="center" style={{ margin: "2" }}>
                <Grid item>
                  <Typography align="left" variant="h6">
                    username
                  </Typography>
                </Grid>
                <Grid item>
                  <TextField
                    type="text"
                    id="userName"
                    value={loginUserName}
                    variant="outlined"
                    onChange={(e) => setLoginUserName(e.target.value)}
                  ></TextField>
                </Grid>
              </Grid>
              <Grid container alignItems="center" spacing={3}>
                <Grid item>
                  <Typography align="left" variant="h6">
                    password
                  </Typography>
                </Grid>
                <Grid item>
                  <TextField
                    type="password"
                    id="password"
                    value={password}
                    variant="outlined"
                    onChange={(e) => setPassword(e.target.value)}
                  ></TextField>
                </Grid>
              </Grid>
            </Grid>
            <Grid
              container
              style={{ marginTop: "1rem", justifyContent: "space-between" }}
            >
              <Grid item>
                {/* <Button
                  variant="contained"
                  onClick={(e) => handleLogin(userName, password, e)}
                  type="submit"
                > */}
                <Button
                  variant="contained"
                  onClick={(e) => validateLogin(e)}
                  type="submit"
                >
                  {/* startIcon={} endIcon={} */}
                  Login
                </Button>
              </Grid>

              <Grid item sx={{ alignItems: "right" }}>
                <Button onClick={handleOpenSignUp}>
                  {/* startIcon={} endIcon={} */}
                  Register/ signup
                </Button>
              </Grid>
            </Grid>
          </form>
        </Paper>
      </Container>
    </>
  );
}
export default LoginModal;
