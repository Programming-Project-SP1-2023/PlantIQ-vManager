package com.plantiq.vsmarthomehub;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.models.User;
import com.plantiq.vsmarthomehub.services.AuthenticationService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class vManager extends Application {


    private User user;
    private String token;
    private String lastPage;

    private final String version = "vManager dev 0.1";

    //public final String baseURI = "http://localhost_tufst:8080";
    public final String baseURI = "https://api-plantiq.azurewebsites.net";

    private static vManager instance;

    private HashMap<String,SmartHomeHub> runningVirtualHubs;

    public vManager(){
        vManager.instance = this;
        this.runningVirtualHubs = new HashMap<>();
    }

    @Override
    public void start(Stage stage) throws IOException {

        if(AuthenticationService.checkExistingLogin()){
            vManager.getInstance().showDashboardStage();
        }else {
            vManager.getInstance().showLoginStage();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static vManager getInstance(){
        return vManager.instance;
    }

    public static Stage getStageById(String id){

        //Get our list of windows
        List<Window> windows = Window.getWindows();

        //Create our stage atomic reference
        AtomicReference<Stage> stage = new AtomicReference<>();

        //loop over all our stages/windows and if we have one with that id
        //we set the stage reference to it.
        windows.forEach((n) -> {
            if(n.getProperties().containsKey("id") && n.getProperties().get("id").equals(id)){
                stage.set((Stage) n);
            }
        });

        //finally return our stage object.
        return  stage.get();
    }

    public void showLoginStage() throws IOException {
        Stage loginStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(vManager.class.getResource("fxml/stages/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        loginStage.setTitle("PlantIQ | login");
        loginStage.setResizable(false);
        loginStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("icons/icon.png"))));
        loginStage.setScene(scene);
        loginStage.getProperties().put("id", "login");
        loginStage.show();
    }

    public void showDashboardStage() throws IOException {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("PlantIQ | vManager");
        dashboardStage.getIcons().add(new Image(Objects.requireNonNull(vManager.class.getResourceAsStream("icons/icon.png"))));
        dashboardStage.setResizable(true);
        dashboardStage.setMaximized(true);

        FXMLLoader fxmlLoader = new FXMLLoader(vManager.class.getResource("fxml/stages/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        dashboardStage.setScene(scene);
        dashboardStage.getProperties().put("id","dashboard");
        dashboardStage.show();
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void setLastPage(String lastPage){
        this.lastPage = lastPage;
    }

    public String getLastPage(){
        return this.lastPage;
    }

    public String getVersion(){
        return this.version;
    }

    public HashMap<String,SmartHomeHub> getRunningVirtualHubs(){
        return this.runningVirtualHubs;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}