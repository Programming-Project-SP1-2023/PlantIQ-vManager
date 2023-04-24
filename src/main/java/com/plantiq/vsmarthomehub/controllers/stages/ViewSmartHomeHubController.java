package com.plantiq.vsmarthomehub.controllers.stages;

import com.plantiq.vsmarthomehub.controllers.components.VirtualSmartHomeHubController;
import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.services.JsonFormatter;
import com.plantiq.vsmarthomehub.tasks.UpdateSmartHomeHub;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ViewSmartHomeHubController implements Initializable {

    @FXML
    private TextFlow json;

    private SmartHomeHub model;

    @FXML
    private Label hubName;

    @FXML
    private Label hubType;

    @FXML
    private TextField nameUpdateField;

    @FXML
    private ComboBox<Integer> frequencyUpdateField;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView loadingSpinner;

    @FXML
    private Rectangle loadingOverlay;

    private static ViewSmartHomeHubController instance;

    public void setSmartHomeHub(SmartHomeHub model){
        this.json.getChildren().clear();
        this.json.getChildren().addAll(JsonFormatter.forTextFlow(model.getJson()));
        this.model = model;
        if(this.model.isVirtual()){
            this.hubType.setText("virtual-asset");
        }else{
            this.hubType.setText("physical-asset");
        }
        this.hubName.setText(this.model.getName());
        this.nameUpdateField.setText(this.model.getName());
        this.frequencyUpdateField.setValue(this.model.getPostFrequency());
        this.cancelButton.setDisable(true);

        this.loadingOverlay.setVisible(false);
        this.loadingSpinner.setVisible(false);
    }


    public void copyJson(){

        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(this.model.getJson());
        clipboard.setContent(content);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ViewSmartHomeHubController.instance = this;

        this.frequencyUpdateField.getItems().add(300);
        this.frequencyUpdateField.getItems().add(600);
        this.frequencyUpdateField.getItems().add(1800);
        this.frequencyUpdateField.getItems().add(3600);
        this.frequencyUpdateField.getItems().add(86400);

        this.cancelButton.setOnAction(event -> {
            this.nameUpdateField.setText(this.model.getName());
            this.frequencyUpdateField.setValue(this.model.getPostFrequency());
            this.cancelButton.setDisable(true);
        });

        this.saveButton.setOnAction(event ->{

            this.model.setName(this.nameUpdateField.getText());
            this.model.setPostFrequency(this.frequencyUpdateField.getValue());

            this.loadingOverlay.setVisible(true);
            this.loadingSpinner.setVisible(true);

            UpdateSmartHomeHub task = new UpdateSmartHomeHub(this.model);

            task.valueProperty().addListener((observableValue, smartHomeHub, t1) -> {ViewSmartHomeHubController.instance.setSmartHomeHub(t1); VirtualSmartHomeHubController.getInstance().refreshTableData();});

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        });

        this.nameUpdateField.textProperty().addListener((observableValue, s, t1) -> {

            ViewSmartHomeHubController.instance.saveButton.setDisable(t1.length() < 2 || t1.equals(s));

            if(!t1.equals(s) || ViewSmartHomeHubController.instance.model.getPostFrequency() != ViewSmartHomeHubController.instance.frequencyUpdateField.getValue()){
                ViewSmartHomeHubController.instance.cancelButton.setDisable(false);
            }

        });

        this.frequencyUpdateField.valueProperty().addListener((observableValue, integer, t1) -> {
            if(!Objects.equals(integer, t1)){
                ViewSmartHomeHubController.instance.cancelButton.setDisable(false);
            }
        });
    }

    public static ViewSmartHomeHubController getInstance(){
        return ViewSmartHomeHubController.instance;
    }

}
