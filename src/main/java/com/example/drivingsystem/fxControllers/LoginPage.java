package com.example.drivingsystem.fxControllers;

import com.example.drivingsystem.*;
import com.example.drivingsystem.utils.DatabaseOperations;
import com.example.drivingsystem.utils.FxUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginPage {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;

    public void validate() {
        try {
            Connection connection = DatabaseOperations.connectToDB();
            if (loginField.getText().trim().isEmpty() || pswField.getText().trim().isEmpty() || (loginField.getText().trim().isEmpty() && pswField.getText().trim().isEmpty())) {
                FxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "Input error", "Fields cannot be empty");
            } else {
                String sql = "SELECT count(*) FROM users d WHERE d.username = ? AND d.password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, loginField.getText());
                preparedStatement.setString(2, pswField.getText());

                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    if (rs.getInt(1) == 0) {
                        FxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "Database error", "No such user.");
                    } else {
                        openMainWindow();
                    }
                }
                DatabaseOperations.disconnectFromDB(connection, preparedStatement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openMainWindow() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/drivingsystem/main-page.fxml"));
        Parent root = loader.load();
        MainPage mp = loader.getController();
        mp.createUserObject(loginField.getText());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        loginField.clear();
        pswField.clear();
    }

    public void register() throws IOException {
        HelloApplication app = new HelloApplication();
        app.changeScene("registration-page.fxml", 700, 500);
    }
}