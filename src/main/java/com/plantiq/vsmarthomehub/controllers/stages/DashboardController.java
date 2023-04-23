package com.plantiq.vsmarthomehub.controllers.stages;

import com.plantiq.vsmarthomehub.controllers.components.SideMenuController;
import com.plantiq.vsmarthomehub.vManager;
import com.plantiq.vsmarthomehub.services.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardController {


    @FXML
    AnchorPane navContainer;

    @FXML
    AnchorPane sideMenuContainer;

    SideMenuController sideMenuController;


    public void logoutButtonPress() throws IOException {

        if(AuthenticationService.logout()){
            vManager.getInstance().showLoginStage();
            vManager.getStageById("dashboard").close();
        }

    }

    @FXML
    public void initialize() throws IOException {
        FXMLLoader menuLoader = new FXMLLoader(vManager.class.getResource("navbar.fxml"));
        AnchorPane menuNodes = menuLoader.load();
        this.navContainer.getChildren().add(menuNodes.getChildren().get(0));

        FXMLLoader sideMenuLoader = new FXMLLoader(vManager.class.getResource("sideMenu.fxml"));
        AnchorPane sideMenuNodes = sideMenuLoader.load();
        this.sideMenuContainer.getChildren().add(sideMenuNodes.getChildren().get(0));
    }



}
