package com.plantiq.vsmarthomehub.controllers.components;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.tasks.FetchSmartHomeHubs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;


public class VirtualSmartHomeHubController {

    private static VirtualSmartHomeHubController instance;

    @FXML
    private TableColumn<SmartHomeHub, String> lastPostedColumn;

    @FXML
    private TableColumn<SmartHomeHub, String> nameColumn;

    @FXML
    private TableColumn<SmartHomeHub, Integer> postFrequencyColumn;

    @FXML
    private TableColumn<SmartHomeHub, Boolean> runningColumn;


    @FXML
    private TableColumn<SmartHomeHub, HBox> actionColumn;

    @FXML
    private TableView<SmartHomeHub> table;

    @FXML
    private ComboBox<Integer> limitSelector;

    @FXML
    private ComboBox<Integer> offsetSelector;

    @FXML
    private TextField tableSearchInput;

    @FXML
    private BorderPane loadingOverlay;

    @FXML
    private BorderPane noHubsFound;

    @FXML
    public void initialize(){

        //Bind the instance
        VirtualSmartHomeHubController.instance = this;

        //Set up the table
        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.table.setPlaceholder(new Label(""));

        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.postFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("postFrequency"));
        this.lastPostedColumn.setCellValueFactory(new PropertyValueFactory<>("lastPostedReadable"));
        this.runningColumn.setCellValueFactory(new PropertyValueFactory<>("running"));
        this.actionColumn.setCellValueFactory(new PropertyValueFactory<>("actionButtons"));
        this.actionColumn.setMinWidth(120);

        //Setup limit selector
        this.limitSelector.getItems().add(5);
        this.limitSelector.getItems().add(10);
        this.limitSelector.getItems().add(25);
        this.limitSelector.getItems().add(50);
        this.limitSelector.getItems().add(75);
        this.limitSelector.getItems().add(100);
        this.limitSelector.setValue(25);

        this.limitSelector.valueProperty().addListener((observableValue, integer, t1) -> this.launchFetchSmartHomeHubsTask());

        //Setup offset selector
        this.offsetSelector.getItems().add(0);
        this.offsetSelector.getItems().add(5);
        this.offsetSelector.getItems().add(10);
        this.offsetSelector.getItems().add(25);
        this.offsetSelector.getItems().add(50);
        this.offsetSelector.getItems().add(75);
        this.offsetSelector.getItems().add(100);
        this.offsetSelector.setValue(0);

        this.offsetSelector.valueProperty().addListener((observableValue, integer, t1) -> this.launchFetchSmartHomeHubsTask());

        //load the data
        this.launchFetchSmartHomeHubsTask();
    }

    public void launchFetchSmartHomeHubsTask(){
        this.loadingOverlay.setVisible(true);

        FetchSmartHomeHubs task = new FetchSmartHomeHubs(this.limitSelector.getValue(),this.offsetSelector.getValue(),true);

        task.valueProperty().addListener((observableValue, smartHomeHubs, t1) -> VirtualSmartHomeHubController.getInstance().setTableData(t1));
        Thread thread = new Thread(task);

        thread.setDaemon(true);
        thread.start();
    }


    public void setTableData(ObservableList<SmartHomeHub> data){
        this.table.setItems(data);
        this.loadingOverlay.setVisible(false);
        this.noHubsFound.setVisible(data.size() == 0);
    }

    public void refreshTableData(){
        this.table.refresh();
    }

    public static VirtualSmartHomeHubController getInstance(){
        return VirtualSmartHomeHubController.instance;
    }


}
