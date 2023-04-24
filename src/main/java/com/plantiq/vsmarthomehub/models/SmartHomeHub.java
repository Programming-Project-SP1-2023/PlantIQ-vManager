package com.plantiq.vsmarthomehub.models;


import com.plantiq.vsmarthomehub.controllers.components.VirtualSmartHomeHubController;
import com.plantiq.vsmarthomehub.core.ModelCollection;
import com.plantiq.vsmarthomehub.vManager;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SmartHomeHub {


    private final String id;
    private final String name;
    private final int lastPosted;
    private final int postFrequency;
    private final boolean virtual;

    private boolean running;
    private Button startButton;

    private Button viewButton;

    private Button deleteButton;

    private HBox actionButtons;

    public static ModelCollection collection(){
        return new ModelCollection();
    }


    public SmartHomeHub(String id, String name, int lastPosted, int postFrequency, boolean virtual){
        this.id =  id;
        this.name = name;
        this.lastPosted = lastPosted;
        this.postFrequency = postFrequency;
        this.virtual = virtual;
        this.running = vManager.getInstance().getRunningVirtualHubs().containsKey(id);
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
}
