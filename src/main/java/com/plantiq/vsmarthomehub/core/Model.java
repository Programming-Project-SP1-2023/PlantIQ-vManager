package com.plantiq.vsmarthomehub.core;

import org.json.JSONObject;

public class Model {

    protected JSONObject data;

    public Model(JSONObject data){
        this.data = data;
    }

    public JSONObject getData(){
        return this.data;
    }
}
