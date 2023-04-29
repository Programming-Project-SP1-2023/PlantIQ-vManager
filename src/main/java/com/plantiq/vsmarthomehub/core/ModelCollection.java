package com.plantiq.vsmarthomehub.core;

import com.plantiq.vsmarthomehub.http.HttpMethods;
import com.plantiq.vsmarthomehub.http.Response;
import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import org.json.JSONArray;
import org.json.JSONObject;

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

        ArrayList<SmartHomeHub> output = new ArrayList<>();


        Response response = Response.fromRequest("/smarthub/all", HttpMethods.GET, parameters.toString(), true);

        if(response.getOutcome() && response.getStatus() == 200){

            if(!response.getData().has("list")){
                return output;
            }else{
                JSONArray results = response.data.getJSONArray("list");

                results.forEach((n)-> output.add(new SmartHomeHub((JSONObject) n)));
            }

        }

        return output;
    }
}
