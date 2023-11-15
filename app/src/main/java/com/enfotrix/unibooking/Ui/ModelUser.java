package com.enfotrix.unibooking.Ui;

public class ModelUser {
     String email,password,userId,name;

    public ModelUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ModelUser(String email, String password, String userId, String name) {
        this.email = email;
        this.password = password;
        this.userId = userId;
        this.name = name;
    }

    public ModelUser(String email) {
        this.email = email;
    }

    public ModelUser(String email, String password, String userId) {

        this.email = email;
        this.password = password;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
