package com.plantiq.vsmarthomehub.controllers.components;

import com.plantiq.vsmarthomehub.models.SmartHomeHub;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class VirtualSmartHomeHubController {


    @FXML
    private TableColumn<SmartHomeHub, Integer> lastPostedColumn;

    @FXML
    private TableColumn<SmartHomeHub, String> nameColumn;

    @FXML
    private TableColumn<SmartHomeHub, Integer> postFrequencyColumn;

    @FXML
    private TableColumn<SmartHomeHub, Boolean> runningColumn;

    @FXML
    private TableColumn<SmartHomeHub, Button> actionColumn;

    @FXML
    private TableView<SmartHomeHub> table;

    private ObservableList<SmartHomeHub> tableData;

    @FXML

    public void initialize(){
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.postFrequencyColumn.setCellValueFactory(new PropertyValueFactory<>("postFrequency"));
        this.lastPostedColumn.setCellValueFactory(new PropertyValueFactory<>("lastPosted"));
        this.runningColumn.setCellValueFactory(new PropertyValueFactory<>("running"));
        this.actionColumn.setCellValueFactory(new PropertyValueFactory<>("startButton"));

        this.tableData = FXCollections.observableArrayList();
        this.updateTableData();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(() -> {
                    table.refresh();
                });
            }
        }, new Date(), 1000);

    }

    private void updateTableData(){

        this.table.setItems(FXCollections.observableList(SmartHomeHub.collection().get()));
    }

}
