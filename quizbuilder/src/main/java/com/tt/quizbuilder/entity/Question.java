package com.tt.quizbuilder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="QUESTIONS")
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="question_id")
    private int id;

    @Column(name="question_text")
    @NotNull
    private String text;

    @OneToMany(fetch=FetchType.LAZY, mappedBy="question", cascade=CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();
    @JsonIgnore
    @ManyToOne(cascade= {CascadeType.ALL})
    private Quiz quiz;

    public Question() {
    }

    public Question(String text) {
        this.text = text;
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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        answers.forEach(x -> x.setQuestion(this));
        this.answers = answers;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
