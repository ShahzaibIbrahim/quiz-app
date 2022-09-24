import StartingPageContent from "../components/StartingPage/StartingPageContent";
import AuthContext from "../store/auth-context";
import QuizList from "../components/Quiz/QuizList";
import React, {useContext} from "react";

const HomePage = () => {
  const authCtx = useContext(AuthContext);
  const isLoggedIn = authCtx.isLoggedIn;

  return (
    <React.Fragment>
      {isLoggedIn && <QuizList />}
      {!isLoggedIn && <StartingPageContent />}
    </React.Fragment>
  );
};

export default HomePage;
