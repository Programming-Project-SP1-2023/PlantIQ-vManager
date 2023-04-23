package com.plantiq.vsmarthomehub.models;


import com.plantiq.vsmarthomehub.core.ModelCollection;
import com.plantiq.vsmarthomehub.vManager;
import javafx.scene.control.Button;

public class SmartHomeHub {


    private final String id;
    private final String name;
    private final int lastPosted;
    private final int postFrequency;
    private final boolean virtual;

    private boolean running;
    private Button startButton;

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
        if(running){
            buttonValue = "Stop";
        }else{
            buttonValue = "Start";
        }
        this.startButton = new Button(buttonValue);
        this.startButton.setOnAction(event -> {

            if(running){

                if(vManager.getInstance().getRunningVirtualHubs().containsKey(id)){
                    vManager.getInstance().getRunningVirtualHubs().remove(this.id);
                }
                this.running = false;
                this.startButton.setText("Start");

            }else{
                if(!vManager.getInstance().getRunningVirtualHubs().containsKey(id)){
                    vManager.getInstance().getRunningVirtualHubs().put(id,this);
                    this.running = true;
                    this.startButton.setText("Stop");
                }
            }

        });
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

    public Button getStartButton(){
        return this.startButton;
    }

    public boolean getRunning(){
        return running;
    }
}
