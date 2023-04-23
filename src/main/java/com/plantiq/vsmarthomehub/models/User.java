package com.plantiq.vsmarthomehub.models;

public class User {

    private String firstname;
    private String lastname;
    private String email;
    private int registrationDate;
    private String id;

    public User(String id, String firstname, String lastname,String email,int registrationDate){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.registrationDate = registrationDate;
    }

    public String getEmail(){
        return this.email;
    }

}
