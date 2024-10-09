module com.alesandro.ejercicio15l {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.alesandro.ejercicio15l to javafx.fxml;
    exports com.alesandro.ejercicio15l;
}