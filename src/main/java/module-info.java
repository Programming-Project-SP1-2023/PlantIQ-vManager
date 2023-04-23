module com.plantiq.vsmarthomehub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires org.json;

    opens com.plantiq.vsmarthomehub to javafx.fxml;
    opens com.plantiq.vsmarthomehub.controllers to javafx.fxml;

    exports com.plantiq.vsmarthomehub.models;
    exports com.plantiq.vsmarthomehub;
    exports com.plantiq.vsmarthomehub.controllers;
    exports com.plantiq.vsmarthomehub.services;
}