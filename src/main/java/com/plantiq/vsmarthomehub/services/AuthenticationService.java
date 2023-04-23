package com.plantiq.vsmarthomehub.services;

import com.plantiq.vsmarthomehub.models.User;
import com.plantiq.vsmarthomehub.vManager;
import org.json.JSONObject;

import java.io.IOException;

public class AuthenticationService {

    public static boolean login(String email, String password){

        String data = "email="+email+"&password="+password;
        String response;
        boolean outcome = false;

        try {
            response = HttpService.postRequest("https://api-plantiq.azurewebsites.net/auth/login", data);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            response = "no response from http request";
        }

        JSONObject result = new JSONObject(response);

        if(result.get("outcome").equals("true")){
            FileService.saveSession(result.getString("session"));
            AuthenticationService.setUser(result.getString("session"));
            outcome = true;
        }


        return outcome;
    }

    public static boolean checkExistingLogin(){
        Boolean outcome = false;

        String session = FileService.loadSession();

        if(session == null){
            return false;
        }
        String response;

        try {
            response = HttpService.getRequest("https://api-plantiq.azurewebsites.net/auth/validate/"+session);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            response = "no response from http request";
        }

        JSONObject result = new JSONObject(response);

        if((boolean) result.get("outcome")){
            outcome = true;
            AuthenticationService.setUser(session);
        }

        return outcome;
    }

    public static boolean logout(){
        String response;


        try {
            response = HttpService.deleteRequest("https://api-plantiq.azurewebsites.net/auth/logout?token="+FileService.loadSession());
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            response = "no response from http request";
        }

        System.out.println(response);

        return true;
    }

    private static void setUser(String token){
        String response = null;
        try {
            response = HttpService.getRequest("https://api-plantiq.azurewebsites.net/user/session/"+token);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }

        if(response != null){
            JSONObject result = new JSONObject(response);
            result = result.getJSONObject("data");
            vManager.getInstance().setUser(new User(result.getString("id"),result.getString("firstname"),result.getString("surname"),result.getString("email"), 0));
        }else{
            vManager.getInstance().setUser(new User("network_error","network_error","network_error","network@error.com", 0));
        }
    }


}
