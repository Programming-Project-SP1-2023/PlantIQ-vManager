package com.plantiq.vsmarthomehub.models;

import com.plantiq.vsmarthomehub.core.Model;
import org.json.JSONObject;

public class User extends Model {

    private String firstname;
    private String lastname;
    private String email;
    private int registrationDate;
    private String id;

    public User(JSONObject data){
        super(data);
    }

    public String getEmail(){
        return this.data.getString("email");
    }

    public String getFirstname(){
        return this.data.getString("firstname");
    }

    public String getLastname(){
        return this.data.getString("lastname");
    }

    public String getRegistrationDate(){
        return this.data.getString("registrationDate");
    }

}
