package com.plantiq.vsmarthomehub.core;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.services.HttpService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ModelCollection {

    private HashMap<String,String> where;

    public ModelCollection where(String id, String value){
        //TODO add later
        return this;
    }

    public ArrayList<SmartHomeHub> get(){
        String response = null;
        ArrayList<SmartHomeHub> output = new ArrayList<>();
        try {
            response = HttpService.getRequest("https://api-plantiq.azurewebsites.net/smarthub/all");
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(response != null){

            JSONObject result = new JSONObject(response);
            JSONArray results = result.getJSONArray("list");

            results.forEach((n)->{

                output.add(new SmartHomeHub(((JSONObject)n).getString("id"),((JSONObject)n).getString("name"),0,0,true));
            });
        }

        return output;
    }
}
