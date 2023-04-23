package com.plantiq.vsmarthomehub.models;


import com.plantiq.vsmarthomehub.vManager;
import javafx.scene.control.Button;

public class SmartHomeHub {


    private final String id;
    private final String name;
    private final String user_id;
    private final int lastPosted;
    private final int postFrequency;
    private final String virtual;

    private boolean running;
    private Button startButton;


    public SmartHomeHub(String id,String name,String user_id,int lastPosted,int postFrequency, String virtual){
        this.id =  id;
        this.name = name;
        this.user_id = user_id;
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
        return this.virtual.equals("true");
    }

    public Button getStartButton(){
        return this.startButton;
    }

    public boolean getRunning(){
        return running;
    }
}
