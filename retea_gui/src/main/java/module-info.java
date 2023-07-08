module com.example.retea_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.retea_gui to javafx.fxml;
    exports com.example.retea_gui;
    exports com.example.retea_gui.ui;
    exports com.example.retea_gui.controllers;
    opens com.example.retea_gui.ui to javafx.fxml;
    opens com.example.retea_gui.controllers to javafx.fxml;

    opens com.example.retea_gui.domain;
}