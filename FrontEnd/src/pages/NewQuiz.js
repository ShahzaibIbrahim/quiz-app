import StartingPageContent from "../components/StartingPage/StartingPageContent";
import AuthContext from "../store/auth-context";
import QuizForm from "../components/Quiz/QuizForm";
import React, {useContext} from "react";

const HomePage = () => {
  const authCtx = useContext(AuthContext);
  const isLoggedIn = authCtx.isLoggedIn;

  return (
    <React.Fragment>
      {isLoggedIn && <QuizForm />}
      {!isLoggedIn && <StartingPageContent />}
    </React.Fragment>
  );
};

export default HomePage;
