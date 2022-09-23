package com.tt.quizbuilder.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tt.quizbuilder.util.QuizIdGenerator;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonFilter("quizFilter")
@Table(name="QUIZ")
public class Quiz {

    @Id
    @GeneratedValue(generator = QuizIdGenerator.generatorName)
    @GenericGenerator(name = QuizIdGenerator.generatorName, strategy = "com.tt.quizbuilder.util.QuizIdGenerator")
    @Column(name="quiz_id")
    private String id;

    @Column(name="quiz_title")
    @NotNull
    @Size(max=255, message = "Quiz title should less than 255 characters")
    private String title;

    @JsonIgnore
    @Column(name="quiz_active")
    @NotNull
    private boolean active = true;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "quiz", cascade=CascadeType.ALL)
    private List<Question> questions = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(cascade= {CascadeType.ALL})
    private User user;

    public Quiz() {
    }

    public Quiz(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        questions.forEach(x -> x.setQuiz(this));
        this.questions = questions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
