module com.example.viruswargame {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires static lombok;

    exports com.example.viruswargame;
    exports com.example.viruswargame.model;
    opens com.example.viruswargame.model to javafx.fxml;
    exports com.example.viruswargame.service;
    opens com.example.viruswargame.service to javafx.fxml;
    exports com.example.viruswargame.utils;
    exports com.example.viruswargame.rmi;
}