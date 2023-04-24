package com.plantiq.vsmarthomehub.models;


import com.plantiq.vsmarthomehub.controllers.components.VirtualSmartHomeHubController;
import com.plantiq.vsmarthomehub.controllers.stages.ViewSmartHomeHubController;
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
import javafx.stage.Stage;
import org.json.JSONObject;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Objects;

public class SmartHomeHub {


    private final String id;
    private String name;
    private int lastPosted;
    private int postFrequency;
    private final boolean virtual;

    private String lastPostedReadable;

    private boolean running;
    private Button startButton;

    private Button viewButton;

    private Button deleteButton;

    private HBox actionButtons;

    private JSONObject json;

    public static ModelCollection collection(){
        return new ModelCollection();
    }


    public SmartHomeHub(String id, String name, int lastPosted, int postFrequency, boolean virtual, String json){
        this.id =  id;
        this.name = name;
        this.lastPosted = lastPosted;
        this.postFrequency = postFrequency;
        this.lastPostedReadable = TimeService.StringFromTimeStamp(lastPosted);
        this.virtual = virtual;
        this.running = vManager.getInstance().getRunningVirtualHubs().containsKey(id);
        this.json = new JSONObject(json);
        String buttonValue;
        String buttonClass;
        if(running){
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
                    vManager.getInstance().getRunningVirtualHubs().remove(this.id);
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

            if(vManager.getStageById(this.id) == null){
                Stage stage = new Stage();
                stage.getProperties().put("id",this.id);
                stage.setResizable(false);
                stage.getIcons().add(new Image(Objects.requireNonNull(vManager.class.getResourceAsStream("icons/icon.png"))));
                stage.setTitle("Smart Home Hub's | "+this.name);
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
                vManager.getStageById(this.id).requestFocus();
            }


        });

        this.deleteButton = new Button("Delete");
        this.deleteButton.setPrefWidth(24);
        this.deleteButton.getStyleClass().add("btn");
        this.deleteButton.getStyleClass().add("btn-danger");

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
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public int getLastPosted(){
        return this.lastPosted;
    }

    public int getPostFrequency(){
        return this.postFrequency;
    }

    public boolean isVirtual(){
        return virtual;
    }

    public boolean getRunning(){
        return running;
    }

    public HBox getActionButtons(){
        return this.actionButtons;
    }

    public String getLastPostedReadable(){
        return this.lastPostedReadable;
    }

    public String getJson(){
        return this.json.toString();
    }

    public void setName(String name){
        this.name = name;
        this.json.put("name",name);
    }

    public void setPostFrequency(int frequency){
        this.postFrequency = frequency;
        this.json.put("postFrequency",frequency);
    }

}
