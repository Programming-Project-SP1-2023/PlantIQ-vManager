package com.plantiq.vsmarthomehub.controllers.components;

import com.plantiq.vsmarthomehub.vManager;
import com.plantiq.vsmarthomehub.services.AuthenticationService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavbarController implements Initializable {


    @FXML
    private Label currentUser;

    public void logoutButtonPress() throws IOException {
        if(AuthenticationService.logout()){
            vManager.getInstance().showLoginStage();
            vManager.getStageById("dashboard").close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.currentUser.setText(vManager.getInstance().getUser().getEmail());
    }
}
