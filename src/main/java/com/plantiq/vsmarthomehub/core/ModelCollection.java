package com.plantiq.vsmarthomehub.core;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.services.HttpService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ModelCollection {

    private HashMap<String,String> where;

    public ModelCollection(){
        this.where = new HashMap<>();
    }

    public ModelCollection where(String id, String value){
        this.where.put(id,value);
        return this;
    }

    public ArrayList<SmartHomeHub> get(){

        StringBuilder parameters = new StringBuilder();
        AtomicInteger count = new AtomicInteger();

        this.where.forEach((key,value)->{
            if(count.getAndIncrement() == 0){
               parameters.append("?");
                parameters.append(key);
                parameters.append("=");
                parameters.append(value);
            }else{
                parameters.append("&");
                parameters.append(key);
                parameters.append("=");
                parameters.append(value);
            }
        });

        String URI = "https://api-plantiq.azurewebsites.net/smarthub/all";
        if(!parameters.isEmpty()){
            URI += parameters.toString();
        }

        String response = null;
        ArrayList<SmartHomeHub> output = new ArrayList<>();
        try {
            response = HttpService.getRequest(URI);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(response != null){

            JSONObject result = new JSONObject(response);
            JSONArray results = result.getJSONArray("list");

            results.forEach((n)->{
                output.add(new SmartHomeHub(((JSONObject)n).getString("id"),((JSONObject)n).getString("name"),((JSONObject)n).getInt("lastPosted"),((JSONObject)n).getInt("postFrequency"),true));
            });
        }

        return output;
    }
}
