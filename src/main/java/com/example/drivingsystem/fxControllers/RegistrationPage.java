package com.example.drivingsystem.fxControllers;

import com.example.drivingsystem.HelloApplication;
import com.example.drivingsystem.utils.DatabaseOperations;
import com.example.drivingsystem.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegistrationPage implements Initializable {
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField mobileNrField;
    @FXML
    public TextField workNrField;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;
    @FXML
    private ComboBox<String> typeChoiceField;
    private String[] choices = {"driver", "manager", "admin"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        typeChoiceField.getItems().addAll(choices);
    }

    public void createUser() throws IOException {
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsert;
        ResultSet resultSet;
        Connection connection = DatabaseOperations.connectToDB();
        if (nameField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty() || birthDateField.getValue() == null || mobileNrField.getText().trim().isEmpty() || workNrField.getText().trim().isEmpty() || loginField.getText().trim().isEmpty() || pswField.getText().trim().isEmpty() || typeChoiceField.getValue() == null) {
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "Input error", "Fields cannot be empty");
        } else {
            try {
                psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? OR password = ? OR (username = ? AND password = ?)");
                psCheckUserExists.setString(1, loginField.getText());
                psCheckUserExists.setString(2, pswField.getText());
                psCheckUserExists.setString(3, loginField.getText());
                psCheckUserExists.setString(4, pswField.getText());
                resultSet = psCheckUserExists.executeQuery();

                if (resultSet.isBeforeFirst()) {
                    FxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "User already exists", "This user is already registered, try logging in.");
                } else {
                    psInsert = connection.prepareStatement("INSERT INTO users(name, surname, birth_date, mobile_number, work_number, username, password, user_types_id) VALUES (?,?,?,?,?,?,?,(SELECT user_types_id from user_types WHERE category=?))");
                    psInsert.setString(1, nameField.getText());
                    psInsert.setString(2, surnameField.getText());
                    String date = birthDateField.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    psInsert.setDate(3, Date.valueOf(date));
                    psInsert.setString(4, mobileNrField.getText());
                    psInsert.setString(5, workNrField.getText());
                    psInsert.setString(6, loginField.getText());
                    psInsert.setString(7, pswField.getText());
                    String data = typeChoiceField.getSelectionModel().getSelectedItem().toString();
                    psInsert.setString(8, data);
                    psInsert.executeUpdate();
                    openMainWindow();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DatabaseOperations.disconnectFromDB(connection, psCheckUserExists);
    }

    public void openMainWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Driving System");
        stage.setScene(scene);
        stage.show();
    }


    public void openLoginWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Driving System");
        stage.setScene(scene);
        stage.show();
    }
}
