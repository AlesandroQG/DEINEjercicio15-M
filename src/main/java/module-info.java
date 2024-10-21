module com.alesandro.ejercicio15m {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires jdk.security.auth;
    requires org.checkerframework.checker.qual;
    requires java.desktop;


    opens com.alesandro.ejercicio15m to javafx.fxml;
    exports com.alesandro.ejercicio15m;
    exports com.alesandro.ejercicio15m.controller;
    opens com.alesandro.ejercicio15m.controller to javafx.fxml;
    exports com.alesandro.ejercicio15m.model;
    exports com.alesandro.ejercicio15m.dao;
}