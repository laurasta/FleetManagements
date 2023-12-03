module com.example.drivingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.drivingsystem to javafx.fxml;
    exports com.example.drivingsystem;
    opens com.example.drivingsystem.fxControllers to javafx.fxml;
    exports com.example.drivingsystem.fxControllers;
    exports com.example.drivingsystem.model;
    opens com.example.drivingsystem.model to javafx.fxml;
}