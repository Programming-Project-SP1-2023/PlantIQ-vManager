package com.plantiq.vsmarthomehub.tasks;

import com.plantiq.vsmarthomehub.http.HttpMethods;
import com.plantiq.vsmarthomehub.http.Response;
import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import javafx.concurrent.Task;

public class UpdateSmartHomeHub extends Task<SmartHomeHub> {

    private final SmartHomeHub model;

    public UpdateSmartHomeHub(SmartHomeHub model){
        this.model = model;
    }


    @Override
    protected SmartHomeHub call() throws Exception {

        Response response = Response.fromRequest("/smarthub/"+this.model.getId(), HttpMethods.PATCH,"name="+this.model.getName()+"&postFrequency="+this.model.getPostFrequency(),true);

        return this.model;
    }
}
