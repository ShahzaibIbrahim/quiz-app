import React, { useState, useRef, useContext } from "react";
import { Button, Paper, TextField, Typography, Alert, Link, Tooltip } from "@mui/material";
import Table from "../UI/Table";
import NewQuestion from "./NewQuestion";
import appConfig from './../../config/config.json';
import AuthContext from "../../store/auth-context";
import BasicModal from "../UI/BasicModal";
import { useHistory } from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';

const columns = [{ id: "id", label: "Sr no.", minWidth: 50 },
{ id: "text", label: "Question Text", minWidth: 200 },
{ id: "action", label: "Action", minWidth: 200 }];

const QuizForm = () => {
  const history = useHistory();
  const authCtx = useContext(AuthContext);
  const [questionList, setQuestionList] = useState([]);
  const [error, setError] = useState(false);
  const [openQuestionModal, setOpenQuestionModal] = useState(false);
  const [openSuccessModal, setOpenSuccessModal] = useState(false);
  const [successMessage, setSuccessMessage] = useState(null);
  const quizTitle = useRef();

  const openQuestionModalHandler = () => {
    setError(false);
    setOpenQuestionModal(!openQuestionModal);
  };

  const openSuccessModalHandler = () => {
    setOpenSuccessModal(!openSuccessModal);
  };

  const questionAddHandler = (quesObject) => {
    questionList.push(quesObject);
  };

  const afterSubmitHandler = () => {
    history.replace('/');
  }

  const submitQuizHandler = () => {
    const qtitle = quizTitle.current.value;

    if (qtitle === null || qtitle.trim().length === 0) {
      setError("Quiz title can not be empty");
    } else if (questionList.length === 0) {
      setError("You need to add atleast one question");
    } else {
      const url = appConfig.api.url + appConfig.endpoints.CREATE_QUIZ;

      fetch(url, {
        method: "POST",
        body: JSON.stringify({ title: qtitle, questions: questionList }),
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
        .then((resData) => {
          
          setOpenSuccessModal(true);
          if(resData.responseCode && resData.responseCode === '01') {
            setSuccessMessage(<p>{resData.message}</p>)
          } else {
            let quizLink = window.location.origin + '/attempt/' + resData.data.quizId;
            setSuccessMessage(<div><p>Quiz Created Successfully</p>
                              <Link href={quizLink}>quizLink</Link></div>);
          }
        })
        .catch((error) => {
          alert(error.message);
        });
    }
  };

  const removeQuestionFromList = (questionIdx) => {
    const temp = [...questionList];

    // removing the element using splice
    temp.splice(questionIdx, 1);

    // updating the list
    setQuestionList(temp);
  }

  return (
    <div>
      <Paper sx={{ width: "80%", margin: "3rem auto", textAlign: "center" }}>
        <Typography id="modal-modal-title" variant="h4" component="h2">
          Create New Quiz
        </Typography>
      </Paper>
      <Paper sx={{ width: "80%", margin: "3rem auto", textAlign: "center" }}>
        {error && <Alert severity="error">{error}</Alert>}
        <TextField
          id="quizTitle"
          inputRef={quizTitle}
          label="Quiz Title"
          style={{ width: "80%" }}
          margin="normal"
          variant="standard"
          inputProps={{ maxLength: 200 }}
          required
        />
      </Paper>
      <Paper sx={{ width: "80%", margin: "3rem auto", textAlign: "center", padding: "20px" }}>
        <Table columns={columns} rows={questionList.map((obj, idx) => ({
          ...obj,
          action: <div>
            <Tooltip title="Delete">
              <Button color="secondary" size="small" onClick={removeQuestionFromList.bind(null, idx)} startIcon={<DeleteIcon />} />
            </Tooltip>
          </div>,
           id: idx + 1
        }))} />
        {questionList && questionList.length < 10 && <Button
          color="secondary"
          fullWidth
          variant="contained"
          onClick={openQuestionModalHandler}
        >
          Add Question
        </Button>
        }
        <NewQuestion
          open={openQuestionModal}
          openHandler={openQuestionModalHandler}
          onAdd={questionAddHandler}
        />
      </Paper>
      <Paper sx={{ width: "80%", margin: "3rem auto", textAlign: "center" }}>
        <Button
          color="secondary"
          fullWidth
          variant="contained"
          onClick={submitQuizHandler}
        >
          Publish Quiz
        </Button>
        <BasicModal open={openSuccessModal} openHandler={openSuccessModalHandler}>
          <p>Quiz Created Successfully. <Link href={successMessage}>{successMessage}</Link></p>
        
          <Button color="secondary"
            fullWidth
            variant="contained"
            onClick={afterSubmitHandler}>
                Ok
          </Button> 
        </BasicModal>
      </Paper>
    </div>
  );
};

export default QuizForm;
