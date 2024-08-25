package com.example.TwitterHTTPalert.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.util.UUID;


@Entity
@Table(schema = "login")
public class ApplicationLogin {

    @Id
    private String id;
    private String username;
    private String password;
    private String fullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
