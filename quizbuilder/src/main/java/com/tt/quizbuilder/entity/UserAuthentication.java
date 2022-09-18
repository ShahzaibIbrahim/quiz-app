package com.tt.quizbuilder.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="USER_AUTH")
public class UserAuthentication {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int authId;

    @Column(name="auth_token")
    private String authToken;

    @Column(name="create_date")
    private Date createDate;

    @Column(name="session_active")
    private boolean sessionActive;

    @JsonIgnore
    @ManyToOne(cascade= {CascadeType.ALL})
    private User user;

    public int getAuthId() {
        return authId;
    }

    public void setAuthId(int authId) {
        this.authId = authId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isSessionActive() {
        return sessionActive;
    }

    public void setSessionActive(boolean sessionActive) {
        this.sessionActive = sessionActive;
    }
}
