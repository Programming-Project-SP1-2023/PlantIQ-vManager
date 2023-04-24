package com.plantiq.vsmarthomehub.controllers.components;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import com.plantiq.vsmarthomehub.tasks.FetchSmartHomeHubs;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;


public class VirtualSmartHomeHubController {

    private static VirtualSmartHomeHubController instance;

    @FXML
    private TableColumn<SmartHomeHub, Integer> lastPostedColumn;

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

    public void initialize(){

        VirtualSmartHomeHubController.instance = this;

        this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.postFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("postFrequency"));
        this.lastPostedColumn.setCellValueFactory(new PropertyValueFactory<>("lastPosted"));
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


        this.launchFetchSmartHomeHubsTask();
    }

    private void launchFetchSmartHomeHubsTask(){
        FetchSmartHomeHubs task = new FetchSmartHomeHubs(this.limitSelector.getValue(),this.offsetSelector.getValue(),true);

        task.valueProperty().addListener((observableValue, smartHomeHubs, t1) -> VirtualSmartHomeHubController.getInstance().setTableData(t1));
        Thread thread = new Thread(task);

        thread.setDaemon(true);
        thread.start();
    }


    public void setTableData(ObservableList<SmartHomeHub> data){
        this.table.setItems(data);
    }

    public void refreshTableData(){
        this.table.refresh();
    }

    public static VirtualSmartHomeHubController getInstance(){
        return VirtualSmartHomeHubController.instance;
    }


}
