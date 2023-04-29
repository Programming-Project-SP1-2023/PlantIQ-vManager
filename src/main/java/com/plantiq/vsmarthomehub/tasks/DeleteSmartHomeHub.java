package com.plantiq.vsmarthomehub.tasks;

import com.plantiq.vsmarthomehub.core.Model;
import com.plantiq.vsmarthomehub.http.HttpMethods;
import com.plantiq.vsmarthomehub.http.Response;
import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import javafx.concurrent.Task;

public class DeleteSmartHomeHub extends Task<Boolean> {

    private final Model model;

    public DeleteSmartHomeHub(SmartHomeHub model){
        this.model = model;
    }


    @Override
    protected Boolean call() throws Exception {

        Response response = Response.fromRequest("/smarthub/"+this.model.getData().getString("id"), HttpMethods.DELETE,null,true);


        return response.getStatus() == 200 && response.getOutcome();
    }
}
