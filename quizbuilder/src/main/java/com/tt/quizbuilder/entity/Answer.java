package com.tt.quizbuilder.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="ANSWERS")
public class Answer {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="answer_id")
    private int id;

    @Column(name="answer_text")
    @NotNull
    private String text;

    @Column(name="answer_is_correct")
    @NotNull
    @JsonIgnore
    private boolean correct;

    @JsonIgnore
    @ManyToOne(cascade= {CascadeType.ALL})
    private Question question;

    public Answer() {

    }

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
