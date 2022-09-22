import { useCallback, useContext, useEffect, useState } from "react";
import AuthContext from "../../store/auth-context";
import appConfig from "../../config/config.json";
import React from "react";
import Table from "../UI/Table";
import { Paper, Tooltip } from '@mui/material';
import Button from '@mui/material/Button';
import DeleteIcon from '@mui/icons-material/Delete';
import OpenInNewIcon from '@mui/icons-material/OpenInNew';
import { ContentCopy } from "@mui/icons-material";

const columns = [
  { id: 'id', label: 'Id', minWidth: 50 },
  { id: 'title', label: 'Title', minWidth: 250 },
  { id: 'action', label: 'Actions', minWidth: 100 },
];

const QuizList = () => {
  const authCtx = useContext(AuthContext);
  const [quizList, setQuizList] = useState([]);

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
        setQuizList(data);
      })
      .catch((error) => {
        alert(error.message);
      });
  }, [setQuizList, authCtx.token]);

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
          const newQuestList = quizList.filter(ques => ques.id !== quizId);
          setQuizList(newQuestList);
        } else {
          let errorMessage = "Could not fetch data";
          throw new Error(errorMessage);
        }
      })
      .catch((error) => {
        alert(error.message);
      });
  }, [setQuizList, quizList, authCtx.token]);

  const getQuizRows = quizList.map(obj => ({
    ...obj,
    action: <div>
      <Tooltip title="Delete">
        <Button color="secondary" size="small" onClick={removeQuiz.bind(null, obj.id)} startIcon={<DeleteIcon />} />
      </Tooltip>
      <Tooltip title="Open in a new tab">
        <Button color="secondary" size="small" onClick={() => { window.open(window.location.origin +'/attempt/' + obj.id); }} startIcon={<OpenInNewIcon />} />
      </Tooltip>
      <Tooltip title="Copy Link">
        <Button color="secondary" size="small" onClick={() => { navigator.clipboard.writeText(window.location.origin +'/attempt/' + obj.id); }} startIcon={<ContentCopy />} />
      </Tooltip>
    </div>

  }));

  return (
    <Paper sx={{ width: '80%', margin: '3rem auto', textAlign: 'center' }}>
      <h1>Quiz List</h1>
      <Table columns={columns} rows={getQuizRows} />
    </Paper>
  );
};

export default QuizList;
