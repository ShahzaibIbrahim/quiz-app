import { useCallback, useContext, useEffect, useState } from "react";
import AuthContext from "../../store/auth-context";
import appConfig from "../../config/config.json";
import React from "react";
import Table from "../UI/Table";
import {Paper} from '@mui/material';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';

const QuizList = () => {
  const authCtx = useContext(AuthContext);
  const [questionList, setQuestionList] = useState([]);

  useEffect(() => {
    const url = appConfig.api.url + appConfig.endpoints.QUIZ_LIST;

    fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: authCtx.token,
      },
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        } else {
          let errorMessage = "Could not fetch data";
          throw new Error(errorMessage);
        }
      })
      .then((data) => {
        //success case
        setQuestionList(data);
      })
      .catch((error) => {
        alert(error.message);
      });
  }, [setQuestionList, authCtx.token]);

  const removeQuiz = useCallback((quizId) => {
    console.log('removing quiz');
    console.log(quizId);

    const url = appConfig.api.url + appConfig.endpoints.REMOVE_QUIZ + "/" + quizId;

    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: authCtx.token,
      },
    })
      .then((res) => {
        if (res.ok) {
          // Update List
          const newQuestList = questionList.filter(ques => ques.id !== quizId);
          setQuestionList(newQuestList);
        } else {
          let errorMessage = "Could not fetch data";
          throw new Error(errorMessage);
        }
      })
      .catch((error) => {
        alert(error.message);
      });
  }, [setQuestionList, questionList, authCtx.token]);

  return (
    <Paper sx={{ width: '80%',  margin: '3rem auto', textAlign: 'center'  }}>
      <h1>Quiz List</h1>
      <Table rows={questionList.map(obj => ({ ...obj, action:  <Button color="secondary" size="small"  onClick={removeQuiz.bind(null, obj.id)} startIcon={<DeleteIcon />}></Button>}))}/>
    </Paper>
  );
};

export default QuizList;
