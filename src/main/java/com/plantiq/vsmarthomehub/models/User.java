package com.plantiq.vsmarthomehub.models;

public class User {

    private String firstname;
    private String lastname;
    private String email;
    private int registrationDate;
    private boolean isAdmin;

    public User(String firstname, String lastname,String email,int registrationDate, boolean isAdmin){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.registrationDate = registrationDate;
        this.isAdmin = isAdmin;
    }
}
