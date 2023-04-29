package com.plantiq.vsmarthomehub.controllers.stages;

import com.plantiq.vsmarthomehub.controllers.components.VirtualSmartHomeHubController;
import com.plantiq.vsmarthomehub.core.Model;
import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.services.JsonFormatter;
import com.plantiq.vsmarthomehub.tasks.DeleteSmartHomeHub;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.json.JSONObject;

public class DeleteModelController {


    private Model model;
    private Stage stage;

    @FXML
    private TextFlow json;

    @FXML
    private Rectangle loadingOverlay;

    @FXML
    private ImageView loadingSpinner;

    private static DeleteModelController instance;

    public void setModel(Model model){
        this.model = model;
        DeleteModelController.instance = this;

        JSONObject data = new JSONObject();
        data.put("name",model.getData().get("name"));

        this.json.getChildren().addAll(JsonFormatter.forTextFlow(data.toString()));

    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void cancel(){
        this.stage.close();
    }

    public void delete(){
        this.loadingOverlay.setVisible(true);
        this.loadingSpinner.setVisible(true);

        DeleteSmartHomeHub task = new DeleteSmartHomeHub((SmartHomeHub) this.model);

        task.valueProperty().addListener((observableValue, smartHomeHubs, t1) -> DeleteModelController.instance.deleteCompleted());
        Thread thread = new Thread(task);

        thread.setDaemon(true);
        thread.start();
    }

    public void deleteCompleted(){
        VirtualSmartHomeHubController.getInstance().launchFetchSmartHomeHubsTask();
        this.stage.close();
    }

}
