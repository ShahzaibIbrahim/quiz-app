import * as React from "react";
import Box from "@mui/material/Box";
import {
  Button,
  TextField,
  Checkbox,
  FormControlLabel,
  Alert,
} from "@mui/material";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 500,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

export default function NewQuestion(props) {
  const open = props.open;
  const [answerList, setAnswerList] = React.useState([]);
  const [error, setError] = React.useState(null);
  const questionTitleRef = React.useRef();

  React.useEffect(() => {
    const answers = [
      { text: "", correct: false },
      { text: "", correct: false },
      { text: "", correct: false },
      { text: "", correct: false },
      { text: "", correct: false },
    ];
    setAnswerList(answers);
  }, [props]);

  const changeAnswerHandler = (answerId, event) => {
    const newAnswerVal = answerList.at(answerId);
    newAnswerVal.text = event.target.value;
  };

  const changeCorrectHandler = (answerId, event) => {
    const newAnswerVal = answerList.at(answerId);
    newAnswerVal.correct = !newAnswerVal.correct;
  };

  const closeModalHandler = () => {
    props.openHandler();
  };

  const onAddQuestion = () => {
    const quesTitle = questionTitleRef.current.value;

    if (quesTitle === null || quesTitle.trim().length === 0) {
      setError("Question Text can not be empty");
    } else if (
      typeof answerList.find((x) => x.text.length > 0) === "undefined"
    ) {
      setError("You need to add atleast one option");
    } else {
      const filteredAnswerList = answerList.filter((x) => x.text.length > 0);
      const returnObject = { text: quesTitle, answers: filteredAnswerList };
      props.onAdd(returnObject);
      props.openHandler();
    }
  };

  return (
    <div>
      <Modal
        open={open}
        onClose={closeModalHandler}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
          {error && <Alert severity="error">{error}</Alert>}
          <Typography id="modal-modal-title" variant="h6" component="h2">
            Add Question
          </Typography>
          <TextField
            key="questionKey"
            id="questionKey"
            fullWidth
            required
            inputRef={questionTitleRef}
            label="Question Title"
            margin="normal"
            variant="standard"
          />
          <Typography id="modal-modal-title" variant="h6" component="h4">
            Add Options
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            (only options that has a value will be added)
          </Typography>
          {answerList.map((answer, id) => (
            <div
              key={"a" + id}
              style={{
                display: "flex",
                justifyContent: "space-evenly",
                alignItems: "flex-end",
              }}
            >
              <TextField
                key={"tf" + id}
                id={id + ""}
                onChange={changeAnswerHandler.bind(null, id)}
                label={"Option " + (id + 1)}
                margin="normal"
                variant="standard"
              />
              <FormControlLabel
                key={"fc" + id}
                control={
                  <Checkbox
                    onChange={changeCorrectHandler.bind(null, id)}
                    key={"cb" + id}
                  />
                }
                label="Is Correct"
              />
            </div>
          ))}
          <Button
            fullWidth
            color="secondary"
            variant="contained"
            onClick={onAddQuestion}
          >
            Add
          </Button>
        </Box>
      </Modal>
    </div>
  );
}
