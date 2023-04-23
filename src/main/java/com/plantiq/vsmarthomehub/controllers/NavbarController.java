package com.plantiq.vsmarthomehub.controllers;

import com.plantiq.vsmarthomehub.vManager;
import com.plantiq.vsmarthomehub.services.AuthenticationService;

import java.io.IOException;

public class NavbarController {


    public void logoutButtonPress() throws IOException {
        if(AuthenticationService.logout()){
            vManager.getInstance().showLoginStage();
            vManager.getStageById("dashboard").close();
        }
    }
}
