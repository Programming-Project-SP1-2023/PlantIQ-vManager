package com.plantiq.vsmarthomehub.services;

import com.plantiq.vsmarthomehub.http.HttpMethods;
import com.plantiq.vsmarthomehub.http.Response;
import com.plantiq.vsmarthomehub.models.User;
import com.plantiq.vsmarthomehub.vManager;
import org.json.JSONObject;

import java.io.IOException;

public class AuthenticationService {

    public static boolean login(String email, String password){

        String data = "email="+email+"&password="+password;

        boolean outcome = false;

        Response response = Response.fromRequest("/auth/login", HttpMethods.POST,data,false);

        if(response.getStatus() == 200 && response.getOutcome()){
            outcome = true;
            FileService.saveSession(response.getContentByKey("session"));
            AuthenticationService.setUser(response.getContentByKey("session"));
        }

        return outcome;
    }

    public static boolean checkExistingLogin(){
        boolean outcome = false;

        String session = FileService.loadSession();

        if(session == null){
            return false;
        }

        Response response = Response.fromRequest("/auth/validate/"+session, HttpMethods.GET,null,false);

        if(response.getStatus() == 200 && response.getOutcome()){
            outcome = true;
            AuthenticationService.setUser(session);
        }

        return outcome;
    }

    public static boolean logout(){

        boolean outcome = false;

        String session = FileService.loadSession();

        Response response = Response.fromRequest("/auth/logout?token="+session, HttpMethods.DELETE,null,true);

        if(response.getStatus() == 200 && response.getOutcome()){
            outcome = true;
        }

        return outcome;
    }

    private static void setUser(String token){

        vManager.getInstance().setToken(token);

        Response response = Response.fromRequest("/user/info",HttpMethods.GET,null,true);

        if(response.getStatus() == 200 && response.getOutcome()){

            vManager.getInstance().setUser(new User(response.getData().getJSONObject("data")));
        }else{
            vManager.getInstance().setUser(new User(new JSONObject()));
        }
    }


}
