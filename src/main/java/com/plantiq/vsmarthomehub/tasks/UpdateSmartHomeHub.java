package com.plantiq.vsmarthomehub.tasks;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.services.HttpService;
import javafx.concurrent.Task;

public class UpdateSmartHomeHub extends Task<SmartHomeHub> {

    private final SmartHomeHub model;

    public UpdateSmartHomeHub(SmartHomeHub model){
        this.model = model;
    }


    @Override
    protected SmartHomeHub call() throws Exception {

        HttpService.patchRequest("http://localhost:8080/smarthub/"+this.model.getId(),"name="+this.model.getName()+"&postFrequency="+this.model.getPostFrequency());
        return this.model;
    }
}
