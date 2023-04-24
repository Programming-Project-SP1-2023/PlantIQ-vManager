package com.plantiq.vsmarthomehub.tasks;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class FetchSmartHomeHubs extends Task<ObservableList<SmartHomeHub>> {

    private boolean virtual;
    int limit;
    int offset;


    public FetchSmartHomeHubs(int limit, int offset, boolean virtual){
        this.virtual = virtual;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    protected ObservableList<SmartHomeHub> call() throws Exception {

        return FXCollections.observableList(
                SmartHomeHub.collection()
                .where("virtual","true")
                .where("limit",String.valueOf(this.limit))
                .where("offset", String.valueOf(this.offset)).get());
    }
}
