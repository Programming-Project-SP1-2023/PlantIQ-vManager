module com.plantiq.vsmarthomehub {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.plantiq.vsmarthomehub to javafx.fxml;
    exports com.plantiq.vsmarthomehub;
}