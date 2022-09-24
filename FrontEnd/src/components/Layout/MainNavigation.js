import { useContext } from "react";
import { Link, useHistory } from "react-router-dom";
import AuthContext from "../../store/auth-context";
import appConfig from "../../config/config.json";

import classes from "./MainNavigation.module.css";

const MainNavigation = () => {
  const authCtx = useContext(AuthContext);
  const history = useHistory();

  const isLoggedin = authCtx.isLoggedIn;

  const logoutHandler = () => {

    const url = appConfig.api.url + appConfig.endpoints.LOGOUT;

    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": authCtx.token
      },
    })
    .then((res) => {
        console.log("Logging out");
        authCtx.logout();
        history.replace('/auth');
    });
  }

  return (
    <header className={classes.header}>
      <Link to="/">
        <div className={classes.logo}>Quiz Builder App</div>
      </Link>
      <nav>
        <ul>
          {isLoggedin && (
            <li>
              <Link to="/">Home</Link>
            </li>
          )}
          {!isLoggedin && (
            <li>
              <Link to="/auth">Login</Link>
            </li>
          )}
          {isLoggedin && (
            <li>
              <Link to="/create">Create Quiz</Link>
            </li>
          )}
          {isLoggedin && (
            <li>
              <button onClick={logoutHandler}>Logout</button>
            </li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default MainNavigation;
