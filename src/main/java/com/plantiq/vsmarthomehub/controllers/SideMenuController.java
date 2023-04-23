package com.plantiq.vsmarthomehub.controllers;

import com.plantiq.vsmarthomehub.vManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SideMenuController {


    @FXML
    Button virtualSmartHomeHubButton;

    @FXML
    Button smartHomeHubButton;

    @FXML
    Button managePlantsButton;

    @FXML
    Button dataExplorerButton;

    @FXML
    Button myAccountButton;

    @FXML
    Button settingsButton;

    @FXML
    VBox menu;

    @FXML
    HBox bottomButtons;


    @FXML
    public void initialize(){
        this.virtualSmartHomeHubButton.setOnAction(event->{
            this.loadPage((Button) event.getSource(), "virtualSmartHomeHubs");
        });
        this.smartHomeHubButton.setOnAction(event -> {
            this.loadPage((Button) event.getSource(), "smartHomeHubs");
        });
        this.managePlantsButton.setOnAction(event -> {
            this.loadPage((Button) event.getSource(), "managePlants");

        });
        this.dataExplorerButton.setOnAction(event -> {
            this.loadPage((Button) event.getSource(), "dataExplorer");
        });
        this.myAccountButton.setOnAction(event -> {
            this.loadPage(this.myAccountButton,"myAccount");
        });
        this.settingsButton.setOnAction(event -> {
            this.loadPage(this.settingsButton,"settings");
        });
    }


    public void loadLastOpenedPage(String page){

        switch(page){
            case "virtualSmartHomeHubs"->{
                this.loadPage(this.virtualSmartHomeHubButton, "virtualSmartHomeHubs");
            }
            case "smartHomeHubs" ->{
                this.loadPage(this.smartHomeHubButton, "smartHomeHubs");
            }
            case "managePlants"->{
                this.loadPage(this.managePlantsButton, "managePlants");
            }
            case "dataExplorer"->{
                this.loadPage(this.dataExplorerButton, "managePlants");
            }
            case "myAccount"->{
                this.loadPage(this.myAccountButton, "myAccount");
            }
            case "settings"->{
                this.loadPage(this.settingsButton, "settings");
            }
        }

    }


    private void loadPage(Button selectedButton,String lastPage){
        vManager.getInstance().setLastPage(lastPage);
        this.setActiveButton(selectedButton);
    }

    private void setActiveButton(Button button){

        //Loop over all menu items
        this.menu.getChildren().forEach((n)->{

            //check if they are the button we want to set
            if(n == button){
                //if so set the color
                button.setStyle("-fx-background-color:  #3d6333");
            }else{
                //else remove the color
                n.setStyle("");
            }
        });

        //Loop over all menu items
        this.bottomButtons.getChildren().forEach((n)->{

            //check if they are the button we want to set
            if(n == button){
                //if so set the color
                button.setStyle("-fx-background-color:  #3d6333");
            }else{
                //else remove the color
                n.setStyle("");
            }
        });
    }
}
