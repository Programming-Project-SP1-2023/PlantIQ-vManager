package com.plantiq.vsmarthomehub.controllers;

import com.plantiq.vsmarthomehub.vManager;
import com.plantiq.vsmarthomehub.services.AuthenticationService;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;


public class LoginController {

    @FXML
    public TextField emailInputField;

    @FXML
    public PasswordField passwordInputField;

    @FXML
    public Label alert;

    public void loginButtonPress() throws IOException {

        if(!emailInputField.getText().isBlank() && !passwordInputField.getText().isBlank()){
            if(AuthenticationService.login(emailInputField.getText(),passwordInputField.getText())){
                vManager.getInstance().showDashboardStage();
                vManager.getStageById("login").close();
            }else{
                alert.setText("Login failed, please check your email and password");
                alert.setStyle("-fx-background-color:  #6b1a1a");
                alert.setVisible(true);
            }
        }else{
            alert.setText("Login failed, please your email & password are not blank");
            alert.setStyle("-fx-background-color:  #a28b28");
            alert.setVisible(true);
        }

    }

    public void registrationButtonPress() throws IOException {
        java.awt.Desktop.getDesktop().browse(URI.create("https://plantiq.azurewebsites.net/"));
    }

}
