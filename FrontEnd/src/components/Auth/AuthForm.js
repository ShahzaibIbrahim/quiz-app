import { useState, useRef, useContext } from "react";
import { useHistory } from "react-router-dom";
import AuthContext from "../../store/auth-context";
import classes from "./AuthForm.module.css";
import appConfig from "../../config/config.json";
import Alert from '@mui/material/Alert';

const AuthForm = () => {
  const emailInputRef = useRef();
  const passwordInputRef = useRef();
  const [isLogin, setIsLogin] = useState(true);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);
  const authCtx = useContext(AuthContext);
  const history = useHistory();

  const switchAuthModeHandler = () => {
    setIsLogin((prevState) => !prevState);
  };

  const submitLoginHandler = (event) => {
    event.preventDefault();

    const enteredEmail = emailInputRef.current.value;
    const enteredPassword = passwordInputRef.current.value;
    setIsLoading(true);
    let url;

    if (isLogin) {
      url = appConfig.api.url + appConfig.endpoints.LOGIN;
    } else {
      url = appConfig.api.url + appConfig.endpoints.REGISTER;
    }

    fetch(url, {
      method: "POST",
      body: JSON.stringify({
        email: enteredEmail,
        password: enteredPassword
      }),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((res) => {
        setIsLoading(false);
        if (res.ok) {
          return res.json();
        } else {
          throw new Error(res);
        }
      })
      .then((resData) => {
        if(resData.responseCode === appConfig.reponseCodes.SUCCESS) {
          authCtx.login(resData.data.authorization);
          history.replace('/');
        }  else if (resData.responseCode === appConfig.reponseCodes.FAILURE) {
          setErrorMessage(resData.message + ". " + resData.violations);
        }
      })
      .catch((error) => {
        setErrorMessage(error.message);
      });
  };

  return (
    <section className={classes.auth}>
      {errorMessage && <Alert severity="error">{errorMessage}</Alert>}
      <h1>{isLogin ? "Login" : "Sign Up"}</h1>
      <form onSubmit={submitLoginHandler}>
        <div className={classes.control}>
          <label htmlFor="email">Your Email</label>
          <input type="email" id="email" ref={emailInputRef} required />
        </div>
        <div className={classes.control}>
          <label htmlFor="password">Your Password</label>
          <input
            type="password"
            id="password"
            minlength="8"
            ref={passwordInputRef}
            required
          />
        </div>
        <div className={classes.actions}>
          {!isLoading && (
            <button>{isLogin ? "Login" : "Create Account"}</button>
          )}
          {isLoading && <p>Loading ...</p>}
          <button
            type="button"
            className={classes.toggle}
            onClick={switchAuthModeHandler}
          >
            {isLogin ? "Create new account" : "Login with existing account"}
          </button>
        </div>
      </form>
    </section>
  );
};

export default AuthForm;
