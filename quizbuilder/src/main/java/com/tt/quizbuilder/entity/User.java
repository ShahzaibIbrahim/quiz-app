package com.tt.quizbuilder.entity;
import com.sun.istack.NotNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="USERS")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private int userId;

    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    @Column(name="user_email", unique = true)
    @Email(message = "Invalid Email Address")
    private String email;

    @NotNull
    @NotEmpty(message = "Password cannot be empty")
    @Column(name="user_password")
    private String password;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "user", cascade=CascadeType.ALL)
    private List<Quiz> quizzes = new ArrayList<>();

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "user", cascade=CascadeType.ALL)
    private List<UserAuthentication> userAuthentications = new ArrayList<>();

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
        this.quizzes.forEach(quiz -> quiz.setUser(this));
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserAuthentication> getUserAuthentications() {
        return userAuthentications;
    }

    public void setUserAuthentications(List<UserAuthentication> userAuthentications) {
        this.userAuthentications = userAuthentications;
    }
}
