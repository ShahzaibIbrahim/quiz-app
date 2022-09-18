package com.tt.quizbuilder.entity;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "Username cannot be empty")
    @Column(name="user_name", unique = true)
    private String username;

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

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
