package com.plantiq.vsmarthomehub;

import com.plantiq.vsmarthomehub.models.User;
import com.plantiq.vsmarthomehub.services.AuthenticationService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class HelloApplication extends Application {


    private User user;
    private static HelloApplication instance;

    public HelloApplication(){
        HelloApplication.instance = this;
    }

    @Override
    public void start(Stage stage) throws IOException {

        if(AuthenticationService.checkExistingLogin()){
            HelloApplication.getInstance().showDashboardStage();
        }else {
            HelloApplication.getInstance().showLoginStage();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    public static HelloApplication getInstance(){
        return HelloApplication.instance;
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
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        loginStage.setTitle("PlantIQ | login");
        loginStage.setResizable(false);
        loginStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("icon.png"))));
        loginStage.setScene(scene);
        loginStage.getProperties().put("id", "login");
        loginStage.show();
    }

    public void showDashboardStage() throws IOException {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("PlantIQ | vManager");
        dashboardStage.getIcons().add(new Image(Objects.requireNonNull(HelloApplication.class.getResourceAsStream("icon.png"))));
        dashboardStage.setResizable(true);
        dashboardStage.setMaximized(true);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        dashboardStage.setScene(scene);
        dashboardStage.getProperties().put("id","dashboard");
        dashboardStage.show();
    }

    public void setUser(User user){
        this.user = user;
    }
}