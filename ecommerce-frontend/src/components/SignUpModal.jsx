import {
  CssBaseline,
  Dialog,
  DialogContent,
  DialogTitle,
  Snackbar,
  SnackbarContent,
  Paper,
  Grid,
  TextField,
  Typography,
  Button,
  DialogActions,
} from "@mui/material";
import { useState } from "react";
import { authService } from "../api/AuthService";

function SignUpModal({ handleCloseSignup, open }) {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [valueValidationFailed, setValueValidationFailed] = useState(false);
  const [failedMessage, setFailedMessgae] = useState("");
  const [validLogin, setValidLogin] = useState(false);

  let handleSubmit = async (e) => {
    e.preventDefault();
    if (
      ("" !== userName.trim() &&
        "" !== password.trim() &&
        "" !== email.trim()) ||
      (userName.length >= 0 && password.length >= 0 && email.length >= 0)
    ) {
      try {
        setValueValidationFailed(false);
        let response = await authService.register(userName, password, email);
        if (response.status === 201) {
          setValidLogin(true);
          handleCloseSignup();
        }
      } catch (error) {
        console.log(error);
        setUserName("");
        setPassword("");
        setEmail("");
        setValueValidationFailed(true);
      }
    } else {
      setUserName("");
      setPassword("");
      setEmail("");
      setValueValidationFailed(true);
    }
  };

  let handleClose = (event, reason) => {
    if (reason === "clickaway") {
      setValueValidationFailed(false);
      return;
    }
    setValueValidationFailed(false);
  };

  return (
    <>
      <Dialog open={open} onClose={handleCloseSignup}>
        <DialogTitle sx={{ color: "black", fontSize: "2rem" }}>
          SignUp or Register
        </DialogTitle>
        <DialogContent>
          <Snackbar
            open={valueValidationFailed}
            autoHideDuration={5000}
            onClose={handleClose}
            sx={{ color: "red" }}
          >
            {validLogin ? (
              <SnackbarContent
                sx={{ backgroundColor: "green", color: "black" }}
                message="successfull registerd!! please login"
              />
            ) : (
              <SnackbarContent
                sx={{ backgroundColor: "red", color: "white" }}
                message="Username or password is wrong or empty, please check!"
              />
            )}
          </Snackbar>
          <CssBaseline />
          <Paper elevation={3} style={{ padding: "20px" }}>
            <form>
              <Grid container direction="column" spacing={3}>
                <Grid
                  container
                  alignItems="center"
                  style={{ margin: "2", justifyContent: "space-between" }}
                >
                  <Grid item>
                    <Typography align="left" variant="h6">
                      email
                    </Typography>
                  </Grid>
                  <Grid item>
                    <TextField
                      type="email"
                      id="email"
                      variant="outlined"
                      onChange={(e) => setEmail(e.target.value)}
                    ></TextField>
                  </Grid>
                </Grid>
                <Grid
                  container
                  alignItems="center"
                  style={{ margin: "2", justifyContent: "space-between" }}
                >
                  <Grid item>
                    <Typography align="left" variant="h6">
                      username
                    </Typography>
                  </Grid>
                  <Grid item>
                    <TextField
                      type="text"
                      id="userName"
                      variant="outlined"
                      onChange={(e) => setUserName(e.target.value)}
                    ></TextField>
                  </Grid>
                </Grid>
                <Grid
                  container
                  alignItems="center"
                  sx={{ justifyContent: "space-between" }}
                >
                  <Grid item>
                    <Typography align="left" variant="h6">
                      password
                    </Typography>
                  </Grid>
                  <Grid item>
                    <TextField
                      type="password"
                      id="password"
                      variant="outlined"
                      onChange={(e) => setPassword(e.target.value)}
                    ></TextField>
                  </Grid>
                </Grid>
              </Grid>
            </form>
          </Paper>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleCloseSignup}>Cancel</Button>
          <Button variant="contained" onClick={handleSubmit}>
            Submit
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
export default SignUpModal;
