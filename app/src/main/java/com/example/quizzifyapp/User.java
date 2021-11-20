package com.example.quizzifyapp;


public class User {
    public String fullName, email, idno, password, role;
    public User(){

    }
    public User(String fullName, String email, String idno, String password, String role){
        this.fullName=fullName;
        this.password=password;
        this.email=email;
        this.idno=idno;
        this.role=role;
    }
}
