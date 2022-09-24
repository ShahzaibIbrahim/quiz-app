import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import appConfig from '../config/config.json';
import { Paper, FormGroup, FormControlLabel, Checkbox, Button, Radio, RadioGroup, FormControl } from '@mui/material';
import BasicModal from '../components/UI/BasicModal';


const AttemptQuizPage = () => {
    let { quizId } = useParams();
    const [data, setData] = useState(null);
    const [openSuccessModal, setOpenSuccessModal] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');
    let questionMap = new Map();
    
    const openSuccessModalHandler = () => {
        setOpenSuccessModal(!openSuccessModal);
    }

    const handleCheckBoxChange = (questionId, answerId, isSingleItem) => {

        // upon selecting any answer we initilize the question map with empty answer array to finally post it to api
        if (questionMap.size === 0) {
            for (const ques of data.questions) {
                questionMap.set(ques.id, []);
            }
        }

        // if it is single-answer question then we clear the array for that particular question and put the single answer against it
        if (isSingleItem) {
            questionMap.get(questionId).length = 0;
            questionMap.get(questionId).push(answerId);
        } else {
            // else if it is multi answer question then we add it in the array of answer against particular question, remove the answer from list in case of un-select
            if (questionMap.get(questionId).find((x) => x === answerId)) {
                const index = questionMap.get(questionId).indexOf(answerId);
                if (index > -1) {
                    questionMap.get(questionId).splice(index, 1);
                }
            } else {
                questionMap.get(questionId).push(answerId);
            }
        }
    }

    const submitQuizHandler = () => {
        // Submitting the quiz to backend
        const url = appConfig.api.url + appConfig.endpoints.SUBMIT_QUIZ;

        fetch(url, {
            method: "POST",
            body: JSON.stringify( {answers : Object.fromEntries(questionMap), quizId : quizId}),
            headers: {
                "Content-Type": "application/json"
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
                // In case of success open the modal with result else show the error message we received from api
                setOpenSuccessModal(true);
                if(resData.responseCode && resData.responseCode === '01') {
                    setSuccessMessage(resData.message);
                } else {
                    setSuccessMessage("You answered "+resData.data.correct+"/"+resData.data.total+" questions correctly");
                }
            })
            .catch((error) => {
                alert(error.message);
            });
    }
    

    useEffect(() => {
        // Whenever the page renders, we get the quiz and render its questions and answers
        const url = appConfig.api.url + appConfig.endpoints.ATTEMPT_QUIZ + '/' + quizId;

        fetch(url, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
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
                if (data.responseCode && data.responseCode === '01') {
                    setData(null);
                } else {
                    setData(data);
                }
            })
            .catch((error) => {
                alert(error.message);
            });
    }, [setData, quizId]);

    return (
        <Paper sx={{ overflow: "auto", maxHeight: "800px", padding: "25px" }}>
            <Paper sx={{ width: '80%', margin: '3rem auto', textAlign: 'left' }}>
                {data && <h3>{data.title}</h3>}
                {data === null && <h3>Quiz not found with id {quizId}</h3>}
            </Paper>
            {data && data !== null && data.questions && data.questions.map((ques, id) => 
                <div>
                    <Paper key={ques.id} sx={{ width: '80%', margin: '3rem auto', textAlign: 'left', padding: '25px' }}>
                        <h4 key={ques.id}>{(id + 1) + '.'} {ques.text}</h4>
                        <p>{!ques.singleAnswer && "Select All Correct Options"}</p>
                        <p>{ques.singleAnswer && "Select One Correct Option"}</p>
                        {!ques.singleAnswer && ques.answers.map((answer) =>
                            <FormGroup key={answer.id}>
                                <FormControlLabel key={answer.id} control={<Checkbox checked={answer.checked} />} label={answer.text} onChange={handleCheckBoxChange.bind(null, ques.id, answer.id, ques.singleAnswer)} />
                            </FormGroup>)}
                        {ques.singleAnswer && 
                        <FormControl>
                            <RadioGroup
                                aria-labelledby="demo-radio-buttons-group-label"
                                name={ques.id}
                            >
                            {ques.answers.map((answer) =>
                                <FormControlLabel key={answer.id} value={answer.id} control={<Radio />} label={answer.text}  onChange={handleCheckBoxChange.bind(null, ques.id, answer.id, ques.singleAnswer)}/>
                             )}
                             </RadioGroup>
                             </FormControl>
                        }
                    </Paper>
                </div>)}
            {data &&
                <Paper  sx={{ width: '80%', margin: '3rem auto', textAlign: 'left', padding: '25px' }}>
                    <Button
                        color="secondary"
                        fullWidth
                        variant="contained"
                        onClick={submitQuizHandler}
                    >
                        Submit
                    </Button>
                </Paper>}

            <BasicModal open={openSuccessModal} openHandler={openSuccessModalHandler}>
                <p>{successMessage}</p>
                <Button color="secondary"
                    fullWidth
                    variant="contained"
                    onClick={() => {window.location.reload();}}>
                    Ok
                </Button>
            </BasicModal>
        </Paper>
    )

}

export default AttemptQuizPage;