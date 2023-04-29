package com.plantiq.vsmarthomehub.tasks;

import com.plantiq.vsmarthomehub.core.Model;
import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.services.HttpService;
import javafx.concurrent.Task;
import org.json.JSONObject;

public class DeleteSmartHomeHub extends Task<Boolean> {

    private final Model model;

    public DeleteSmartHomeHub(SmartHomeHub model){
        this.model = model;
    }


    @Override
    protected Boolean call() throws Exception {

        JSONObject result = new JSONObject(HttpService.deleteRequest("https://api-plantiq.azurewebsites.net/smarthub/" + this.model.getData().getString("id"),true));

        return result.getString("outcome").equals("true");
    }
}
