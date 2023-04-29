package com.plantiq.vsmarthomehub.models;


import com.plantiq.vsmarthomehub.controllers.components.VirtualSmartHomeHubController;
import com.plantiq.vsmarthomehub.controllers.stages.DeleteModelController;
import com.plantiq.vsmarthomehub.controllers.stages.ViewSmartHomeHubController;
import com.plantiq.vsmarthomehub.core.Model;
import com.plantiq.vsmarthomehub.core.ModelCollection;
import com.plantiq.vsmarthomehub.services.TimeService;
import com.plantiq.vsmarthomehub.vManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONObject;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Objects;

public class SmartHomeHub extends Model {



    private boolean running;
    private Button startButton;

    private Button viewButton;

    private Button deleteButton;

    private HBox actionButtons;


    public static ModelCollection collection(){
        return new ModelCollection();
    }


    public SmartHomeHub(JSONObject data){
        super(data);
        String id = data.getString("id");

        this.running = vManager.getInstance().getRunningVirtualHubs().containsKey(id);
        String buttonValue;
        String buttonClass;
        if(this.running){
            buttonValue = "Stop";
            buttonClass = "btn-danger";
        }else{
            buttonValue = "Start";
            buttonClass = "btn-success";
        }
        this.startButton = new Button(buttonValue);
        this.startButton.getStyleClass().add(buttonClass);
        this.startButton.getStyleClass().add("btn");
        this.startButton.setPrefWidth(24);
        this.startButton.setOnAction(event -> {

            if(running){

                if(vManager.getInstance().getRunningVirtualHubs().containsKey(id)){
                    vManager.getInstance().getRunningVirtualHubs().remove(id);
                }
                this.running = false;
                this.startButton.setText("Start");
                this.startButton.getStyleClass().remove("btn-danger");
                this.startButton.getStyleClass().add("btn-success");

            }else{
                if(!vManager.getInstance().getRunningVirtualHubs().containsKey(id)){
                    vManager.getInstance().getRunningVirtualHubs().put(id,this);
                    this.running = true;
                    this.startButton.setText("Stop");
                    this.startButton.getStyleClass().remove("btn-success");
                    this.startButton.getStyleClass().add("btn-danger");
                }
            }

            //refresh this page if its open!
            if(VirtualSmartHomeHubController.getInstance() != null){
                VirtualSmartHomeHubController.getInstance().refreshTableData();
            }

        });

        this.viewButton = new Button("View");
        this.viewButton.setPrefWidth(24);
        this.viewButton.getStyleClass().add("btn");
        this.viewButton.getStyleClass().add("btn-primary2");
        this.viewButton.setOnAction((event)->{

            if(vManager.getStageById("VIEW"+id) == null){
                Stage stage = new Stage();
                stage.getProperties().put("id",id);
                stage.setResizable(false);
                stage.getIcons().add(new Image(Objects.requireNonNull(vManager.class.getResourceAsStream("icons/icon.png"))));
                stage.setTitle("Smart Home Hub's | "+this.data.getString("name"));
                FXMLLoader loader = new FXMLLoader(vManager.class.getResource("fxml/stages/viewSmartHomeHub.fxml"));
                try {
                    Scene scene = new Scene(loader.load());
                    scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                    stage.setScene(scene);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ViewSmartHomeHubController controller = loader.getController();
                controller.setSmartHomeHub(this);
                stage.show();
            }else{
                vManager.getStageById(id).requestFocus();
            }


        });

        this.deleteButton = new Button("Delete");
        this.deleteButton.setPrefWidth(24);
        this.deleteButton.getStyleClass().add("btn");
        this.deleteButton.getStyleClass().add("btn-danger");
        this.deleteButton.setOnAction((event -> {

            if(vManager.getStageById("DELETE"+id) == null){

                Stage stage = new Stage();
                stage.getProperties().put("id",id);
                stage.setResizable(false);
                stage.getIcons().add(new Image(Objects.requireNonNull(vManager.class.getResourceAsStream("icons/icon.png"))));
                stage.setTitle("Delete | "+this.data.getString("name"));
                stage.initModality(Modality.APPLICATION_MODAL);
                FXMLLoader loader = new FXMLLoader(vManager.class.getResource("fxml/stages/deleteModel.fxml"));
                try {
                    Scene scene = new Scene(loader.load());
                    scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
                    stage.setScene(scene);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                DeleteModelController controller = loader.getController();
                controller.setModel(this);
                controller.setStage(stage);
                stage.show();
            }else{
                vManager.getStageById(id).requestFocus();
            }
        }));

        this.actionButtons = new HBox();
        this.actionButtons.setSpacing(8);
        HBox.setHgrow(this.actionButtons, Priority.ALWAYS);
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        this.actionButtons.getChildren().add(this.startButton);
        this.actionButtons.getChildren().add(region1);
        this.actionButtons.getChildren().add(this.viewButton);
        this.actionButtons.getChildren().add(region2);
        this.actionButtons.getChildren().add(this.deleteButton);
    }

    public String getId(){
        return this.data.getString("id");
    }

    public String getName(){
        return this.data.getString("name");
    }

    public int getLastPosted(){
        return this.data.getInt("lastPosted");
    }

    public int getPostFrequency(){
        return this.data.getInt("postFrequency");
    }

    public boolean isVirtual(){
        return this.data.getBoolean("virtual");
    }

    public boolean getRunning(){
        return running;
    }

    public HBox getActionButtons(){
        return this.actionButtons;
    }

    public String getLastPostedReadable(){
        return TimeService.StringFromTimeStamp(this.data.getInt("lastPosted"));
    }

    public String getJson(){
        return this.data.toString();
    }

    public void setName(String name){
        this.data.put("name",name);
    }

    public void setPostFrequency(int frequency){
        this.data.put("postFrequency",frequency);
    }

}
