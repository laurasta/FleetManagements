package com.example.drivingsystem.fxControllers;

import com.example.drivingsystem.*;
import com.example.drivingsystem.model.*;
import com.example.drivingsystem.model.Driver;
import com.example.drivingsystem.utils.DatabaseOperations;
import com.example.drivingsystem.utils.FxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.example.drivingsystem.utils.DatabaseOperations.connectToDB;

public class MainPage implements Initializable {
    //User tab
    public TextField id;
    public TextField tfName;
    public TextField tfSurname;
    public DatePicker tfBirth_date;
    public TextField tfMob;
    public TextField tfWork;
    public TextField tfUsername;
    public TextField tfPassword;
    public ComboBox<String> tfTypeCombo;

    public TableColumn idCol;
    public TableColumn nameCol;
    public TableColumn surnameCol;
    public TableColumn birthDateCol;
    public TableColumn mobNrCol;
    public TableColumn workNrCol;
    public TableColumn passwordCol;
    public TableColumn typeCol;
    public TableView tvUsers;
    public TableColumn usernameCol;

    //DESTINATION
    public TextField dIdTx;
    public TextField dFromTx;
    public TextField dToTx;
    public TextField dStopTx;
    public TextField dDistTx;
    public TableColumn dIdCol;
    public TableColumn dFromCol;
    public TableColumn dToCol;
    public TableColumn dStopCol;
    public TableColumn dDistCol;


    //VEHICLE
    public TableColumn vhIdCol;
    public TableColumn vhMakeCol;
    public TableColumn vhModelCol;
    public TableColumn vhPlateCol;

    public TextField vhMake;
    public TextField vhModel;
    public TextField vhPlate;
    public TextField vhId;
    public TableView tvVehicle;
    public Button signOutBtn;
    public TableColumn vhStateCol;
    public ComboBox vhState;
    public TableView tvDestination;
    public TableColumn cIdCol;
    public TableColumn cDescCol;
    public TableColumn cWeighCol;
    public TableColumn cStatCol;
    public TextField cDescTx;
    public TextField cIdTx;
    public ComboBox cStatCb;
    public TextField cWeighTx;
    public TableView tvCargo;
    public TableView tvOrder;
    public TableColumn oIdCol;
    public TableColumn oDesIdCol;
    public TableColumn oCargIdCol;
    public TableColumn oVehIdCol;
    public TableColumn oAssIdCol;
    public TableColumn oStatCol;
    public TextField oDesTx;
    public TextField oVehTx;
    public TextField oAssTx;
    public TextField oIdTx;
    public ComboBox oStatCb;
    public TextField oCargTx;
    public TextField accName;
    public TextField accSurname;
    public DatePicker accBirtDate;
    public TextField accMobNr;
    public TextField accWorkNr;
    public TextField accUsername;
    public TextField accPassword;
    public Button btnAddUser;
    public Button btnDeleteUser;
    public Button btnUpdateUser;
    public Tab OrdersTab;
    public Tab destinationsTab;
    public Tab cargoTab;
    public Pane tripsPane;
    public Pane AssignOrdersPane;
    public TextField currOrderID;
    public TextField currOrderStatus;
    public ComboBox destinationIdOrder;
    public ComboBox cargoIdOrder;
    public ComboBox vehicleIdOrder;
    public ComboBox AssigneeIdOrder;
    public TableColumn dStatusCol;
    public TextField dStatusTx;
    public ComboBox orderIdOrder;
    public TableColumn oAssigneeIdCol;
    public BarChart barChart;
    public Button addOrder;
    public Button updateOrder;
    public Button deleteOrder;
    @FXML
    Tab usersTab;
    @FXML
    Tab vehiclesTab;
    @FXML
    Tab forumsTab;
    @FXML
    Tab profileTab;

    private String[] userChoices = {"driver", "manager", "admin"};
    private String[] orderChoices = {"waiting", "in progress", "completed"};
    private String[] vhStateChoices = {"not selected", "selected", "in repair"};
    private String[] cargoStates = {"not assigned", "assigned"};

    @FXML
    private TreeView<Comment> commentTree;
    @FXML
    private TextField commentTextField;
    @FXML
    private TextField usernameTextField;

    private CommentingSystem commentSystem;
    User currUser;
    int currUserId;
    int driverOrderId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tfTypeCombo.getItems().addAll(userChoices);
        vhState.getItems().addAll(vhStateChoices);
        cStatCb.getItems().addAll(cargoStates);
        oStatCb.getItems().addAll(orderChoices);
        this.showUser();
        this.showVehicle();
        this.showDestination();
        this.showCargo();
        this.showOrder();
        try {
            this.ordersBarChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        commentSystem = new CommentingSystem();
        commentTree.setRoot(buildCommentTree(commentSystem.getCommentList()));
        commentTree.setCellFactory(treeView -> new CommentCell());
    }

    public void SignOutButton(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) signOutBtn.getScene().getWindow();
        stage.close();
    }

    public void createUserObject(String username) throws SQLException {
        User user;
        Connection connection = connectToDB();
        PreparedStatement ps = connection.prepareStatement("SELECT users.user_id, users.name, users.surname, users.birth_date, users.mobile_number, users.work_number, users.username, users.password, user_types.category FROM users INNER JOIN user_types ON users.user_types_id = user_types.user_types_id WHERE username = ?;");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            switch (rs.getString("category")) {
                case "admin":
                    user = new Administratorius(currUserId = rs.getInt("user_id"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(), rs.getString("mobile_number"), rs.getString("work_number"), rs.getString("username"), rs.getString("password"), rs.getString("category"));
                    currUser = user;
                    showProfileInfo();
                    break;
                case "manager":
                    user = new Manager(currUserId = rs.getInt("user_id"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(), rs.getString("mobile_number"), rs.getString("work_number"), rs.getString("username"), rs.getString("password"), rs.getString("category"));
                    currUser = user;
                    showProfileInfo();
                    break;
                case "driver":
                    user = new Driver(currUserId = rs.getInt("user_id"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(), rs.getString("mobile_number"), rs.getString("work_number"), rs.getString("username"), rs.getString("password"), rs.getString("category"));
                    currUser = user;
                    showProfileInfo();
                    ShowDriverOrder();
                    break;
            }
        }
        //update'inti profilius
        setPermissions();
    }

    public void setPermissions() {
        if (currUser.getType().equals("admin")) {
            tripsPane.setVisible(false);
            System.out.println("Hi, I'm admin");
        }
        switch (currUser.getType()) {
            case "driver":
                System.out.println("Hi, I'm driver");
                usersTab.setDisable(true);
                vehiclesTab.setDisable(true);
//                OrdersTab.setDisable(true);
                addOrder.setDisable(true);
                updateOrder.setDisable(true);
                deleteOrder.setDisable(true);
                destinationsTab.setDisable(true);
                cargoTab.setDisable(true);
                AssignOrdersPane.setVisible(false);
                break;
            case "manager":
                System.out.println("Hi, I'm manager");
                tripsPane.setVisible(false);
                btnDeleteUser.setVisible(false);
                btnAddUser.setVisible(false);
                btnUpdateUser.setVisible(false);
                tripsPane.setVisible(false);
                break;
        }
    }

    @FXML
    void submitComment(ActionEvent event) {
        TreeItem<Comment> selectedNode = commentTree.getSelectionModel().getSelectedItem();
        String username = usernameTextField.getText();
        if (username.isEmpty()) {
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "No username", "Please write your username.");
        } else if (commentTextField.getText().isEmpty()) {
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "No text", "Please write your message.");
        } else {
            Comment parentComment = (selectedNode == null) ? null : selectedNode.getValue();
            Comment newComment = new Comment(commentSystem.getCommentList().size(), commentTextField.getText(), parentComment == null ? -1 : parentComment.getCommentId(), username);
            commentSystem.addComment(newComment);
            TreeItem<Comment> newCommentNode = new TreeItem<>(newComment);
            if (selectedNode == null) {
                commentTree.getRoot().getChildren().add(newCommentNode);
            } else {
                selectedNode.getChildren().add(newCommentNode);
            }
            commentTree.refresh();
        }
    }

    private TreeItem<Comment> buildCommentTree(ArrayList<Comment> comments) {
        if (comments == null) {
            return new TreeItem<>();
        }
        TreeItem<Comment> root = new TreeItem<>();
        for (Comment comment : comments) {
            TreeItem<Comment> commentNode = new TreeItem<>(comment);
            commentNode.setExpanded(true);
            commentNode.getChildren().addAll(buildCommentTree(comment.getSubComments()).getChildren());
            root.getChildren().add(commentNode);
        }
        return root;
    }

    public void UpdateProfileInfo(ActionEvent actionEvent) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement psUpdate;
        try {
            psUpdate = connection.prepareStatement("UPDATE users SET name  = ?, surname = ?, birth_date = ?, mobile_number = ?, work_number = ?, username = ?, password = ? WHERE user_id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        psUpdate.setString(1, accName.getText());
        psUpdate.setString(2, accSurname.getText());
        String date = accBirtDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        psUpdate.setDate(3, Date.valueOf(date));
        psUpdate.setString(4, accMobNr.getText());
        psUpdate.setString(5, accWorkNr.getText());
        psUpdate.setString(6, accUsername.getText());
        psUpdate.setString(7, accPassword.getText());
        psUpdate.setInt(8, currUserId);
        psUpdate.executeUpdate();
        this.showUser();
        DatabaseOperations.disconnectFromDB(connection, psUpdate);
    }

    public void showProfileInfo() {
        accName.setText(currUser.getName());
        accSurname.setText(currUser.getSurname());
        accBirtDate.setValue(currUser.getBirth_date());
        accMobNr.setText(currUser.getMobile_number());
        accWorkNr.setText(currUser.getWork_number());
        accUsername.setText(currUser.getUsername());
        accPassword.setText(currUser.getPassword());
    }

    public void buttonAddUser(ActionEvent actionEvent) {
        PreparedStatement psCheckUserExists = null;
        PreparedStatement psInsert;
        ResultSet resultSet;
        Connection connection = connectToDB();
        try {
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? OR password = ? OR (username = ? AND password = ?)");
            psCheckUserExists.setString(1, tfUsername.getText());
            psCheckUserExists.setString(2, tfPassword.getText());
            psCheckUserExists.setString(3, tfUsername.getText());
            psCheckUserExists.setString(4, tfPassword.getText());
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                FxUtils.alertMessage(Alert.AlertType.ERROR, "Error", "User already exists", "This user is already registered, try logging in.");
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users(name, surname, birth_date, mobile_number, work_number, username, password, user_types_id) VALUES (?,?,?,?,?,?,?,(SELECT user_types_id from user_types WHERE category=?))");
                psInsert.setString(1, tfName.getText());
                psInsert.setString(2, tfSurname.getText());
                String date = tfBirth_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                psInsert.setDate(3, Date.valueOf(date));
                psInsert.setString(4, tfMob.getText());
                psInsert.setString(5, tfWork.getText());
                psInsert.setString(6, tfUsername.getText());
                psInsert.setString(7, tfPassword.getText());
                String data = tfTypeCombo.getSelectionModel().getSelectedItem().toString();
                psInsert.setString(8, data);
                psInsert.executeUpdate();

                FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Added successfully", "This information added to the database.");
            }
            showUser();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        DatabaseOperations.disconnectFromDB(connection, psCheckUserExists);
    }

    public ObservableList<User> getUserList() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        Connection conn = connectToDB();
        String query = "SELECT users.user_id, users.name, users.surname, users.birth_date, users.mobile_number, users.work_number, users.username, users.password, user_types.category FROM users INNER JOIN user_types ON users.user_types_id = user_types.user_types_id;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                User user = new User(rs.getInt("user_id"), rs.getString("name"), rs.getString("surname"), rs.getDate("birth_date").toLocalDate(), rs.getString("mobile_number"), rs.getString("work_number"), rs.getString("username"), rs.getString("password"), rs.getString("category"));
                userList.add(user);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return userList;
    }

    public void showUser() {
        ObservableList<User> list = this.getUserList();
        this.idCol.setCellValueFactory(new PropertyValueFactory("user_id"));
        this.nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        this.surnameCol.setCellValueFactory(new PropertyValueFactory("surname"));
        this.birthDateCol.setCellValueFactory(new PropertyValueFactory("birth_date"));
        this.mobNrCol.setCellValueFactory(new PropertyValueFactory("mobile_number"));
        this.workNrCol.setCellValueFactory(new PropertyValueFactory("work_number"));
        this.usernameCol.setCellValueFactory(new PropertyValueFactory("username"));
        this.passwordCol.setCellValueFactory(new PropertyValueFactory("password"));
        this.typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        this.tvUsers.setItems(list);
    }

    public void ButtonUpdateUser(ActionEvent actionEvent) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement psUpdate;
        try {
            psUpdate = connection.prepareStatement("UPDATE users SET name  = ?, surname = ?, birth_date = ?, mobile_number = ?, work_number = ?, username = ?, password = ?, user_types_id = (SELECT user_types_id from user_types WHERE category=?) WHERE user_id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        psUpdate.setString(1, tfName.getText());
        psUpdate.setString(2, tfSurname.getText());
        String date = tfBirth_date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        psUpdate.setDate(3, Date.valueOf(date));
        psUpdate.setString(4, tfMob.getText());
        psUpdate.setString(5, tfWork.getText());
        psUpdate.setString(6, tfUsername.getText());
        psUpdate.setString(7, tfPassword.getText());
        String data = tfTypeCombo.getSelectionModel().getSelectedItem().toString();
        psUpdate.setString(8, data);
        int idNr = Integer.parseInt(id.getText());
        psUpdate.setInt(9, idNr);
        psUpdate.executeUpdate();
        this.showUser();
        DatabaseOperations.disconnectFromDB(connection, psUpdate);
    }

    public void ButtonDeleteUser(ActionEvent actionEvent) {
        String query = "DELETE FROM users WHERE user_id =" + this.id.getText() + "";
        this.executeQuery(query);
        this.showUser();
    }

    private void executeQuery(String query) {
        Connection conn = connectToDB();

        try {
            Statement st = conn.createStatement();
            st.executeUpdate(query);
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }

    @FXML
    public void handleMouseActionUser(MouseEvent mouseEvent) {
        User user = (User) tvUsers.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(user.getUser_id()));
        tfName.setText(user.getName());
        tfSurname.setText(user.getSurname());
        tfBirth_date.setValue(user.getBirth_date());
        tfMob.setText(user.getMobile_number());
        tfWork.setText(user.getWork_number());
        tfUsername.setText(user.getUsername());
        tfPassword.setText(user.getPassword());
        tfTypeCombo.setValue(user.getType());
    }

    public void buttonAddVehicle(ActionEvent actionEvent) {
        PreparedStatement psInsert;
        Connection connection = connectToDB();
        try {
            psInsert = connection.prepareStatement("INSERT INTO vehicles (make, model, plate, state) VALUES (?,?,?,?)");
            psInsert.setString(1, vhMake.getText());
            psInsert.setString(2, vhModel.getText());
            psInsert.setString(3, vhPlate.getText());
            String data = vhState.getSelectionModel().getSelectedItem().toString();
            psInsert.setString(4, data);
            psInsert.executeUpdate();
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Added successfully", "This information is added to the database.");
            showVehicle();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        DatabaseOperations.disconnectFromDB(connection, psInsert);
    }

    public void ButtonDeleteVehicle(ActionEvent actionEvent) {
        String query = "DELETE FROM vehicles WHERE id =" + this.vhId.getText() + "";
        this.executeQuery(query);
        FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Deleted successfully", "Vehicle information deleted from database.");
        this.showVehicle();
    }

    public void ButtonUpdateVehicle(ActionEvent actionEvent) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement psUpdate;
        try {
            psUpdate = connection.prepareStatement("UPDATE vehicles SET make = ?, model = ?, plate = ?, state = ? WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        psUpdate.setString(1, vhMake.getText());
        psUpdate.setString(2, vhModel.getText());
        psUpdate.setString(3, vhPlate.getText());
        String data = vhState.getSelectionModel().getSelectedItem().toString();
        psUpdate.setString(4, data);
        psUpdate.setInt(5, Integer.parseInt(vhId.getText()));
        psUpdate.executeUpdate();
        this.showVehicle();
        DatabaseOperations.disconnectFromDB(connection, psUpdate);
    }

    public void handleMouseClickVehicle(MouseEvent mouseEvent) {
        Vehicle vehicle = (Vehicle) tvVehicle.getSelectionModel().getSelectedItem();
        vhId.setText(String.valueOf(vehicle.getId()));
        vhMake.setText(vehicle.getMake());
        vhModel.setText(vehicle.getModel());
        vhPlate.setText(vehicle.getPlate());
        vhState.setValue(vehicle.getState());
    }

    public ObservableList<Vehicle> getVehicleList() {
        ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();
        Connection conn = connectToDB();
        String query = "SELECT id, make, model, plate, state FROM vehicles;";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt("id"), rs.getString("make"), rs.getString("model"), rs.getString("plate"), rs.getString("state"));
                vehicleList.add(vehicle);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return vehicleList;
    }

    public void showVehicle() {
        ObservableList<Vehicle> list = this.getVehicleList();
        this.vhIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.vhMakeCol.setCellValueFactory(new PropertyValueFactory<Order, String>("make"));
        this.vhModelCol.setCellValueFactory(new PropertyValueFactory("model"));
        this.vhPlateCol.setCellValueFactory(new PropertyValueFactory("plate"));
        this.vhStateCol.setCellValueFactory(new PropertyValueFactory("state"));
        this.tvVehicle.setItems(list);
    }

    public void buttonAddCargo(ActionEvent actionEvent) {
        PreparedStatement psInsert;
        Connection connection = connectToDB();
        try {
            psInsert = connection.prepareStatement("INSERT INTO cargo (description, weight, status) VALUES (?,?,?)");
            psInsert.setString(1, cDescTx.getText());
            psInsert.setInt(2, Integer.parseInt(cWeighTx.getText()));
            psInsert.setString(3, String.valueOf(cStatCb.getValue()));
            psInsert.executeUpdate();
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Added successfully", "This information is added to the database.");
            showCargo();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        DatabaseOperations.disconnectFromDB(connection, psInsert);
    }

    public void ButtonDeleteCargo(ActionEvent actionEvent) {
        String query = "DELETE FROM cargo WHERE id =" + this.cIdTx.getText() + "";
        this.executeQuery(query);
        FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Deleted successfully", "Vehicle information deleted from database.");
        this.showCargo();
    }

    public void ButtonUpdateCargo(ActionEvent actionEvent) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement psUpdate;
        try {
            psUpdate = connection.prepareStatement("UPDATE cargo SET description  = ?, weight = ?, status = ? WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        psUpdate.setString(1, cDescTx.getText());
        psUpdate.setInt(2, Integer.parseInt(cWeighTx.getText()));
        psUpdate.setString(3, String.valueOf(cStatCb.getValue()));
        psUpdate.setInt(4, Integer.parseInt(cIdTx.getText()));
        psUpdate.executeUpdate();
        this.showCargo();
        DatabaseOperations.disconnectFromDB(connection, psUpdate);
    }

    private void showCargo() {
        ObservableList<Cargo> list = this.getCargoList();
        this.cIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.cDescCol.setCellValueFactory(new PropertyValueFactory("description"));
        this.cWeighCol.setCellValueFactory(new PropertyValueFactory("weight"));
        this.cStatCol.setCellValueFactory(new PropertyValueFactory("status"));
        this.tvCargo.setItems(list);
    }

    private ObservableList<Cargo> getCargoList() {
        ObservableList<Cargo> cargoList = FXCollections.observableArrayList();
        Connection conn = connectToDB();
        String query = "SELECT id, description, weight, status from cargo";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Cargo cargo = new Cargo(rs.getInt("id"), rs.getString("description"), rs.getInt("weight"), rs.getString("status"));
                cargoList.add(cargo);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return cargoList;
    }

    public void handleMouseClickCargo(MouseEvent mouseEvent) {
        Cargo cargo = (Cargo) tvCargo.getSelectionModel().getSelectedItem();
        cIdTx.setText(String.valueOf(cargo.getId()));
        cDescTx.setText(cargo.getDescription());
        cWeighTx.setText(String.valueOf(cargo.getWeight()));
        cStatCb.setValue(cargo.getStatus());
    }

    public void buttonAddDestination(ActionEvent actionEvent) {
        PreparedStatement psInsert;
        Connection connection = connectToDB();
        try {
            psInsert = connection.prepareStatement("INSERT INTO destination (from_where, to_where, stop, distance, status) VALUES (?,?,?,?,?)");
            psInsert.setString(1, dFromTx.getText());
            psInsert.setString(2, dToTx.getText());
            psInsert.setString(3, dStopTx.getText());
            psInsert.setInt(4, Integer.parseInt(dDistTx.getText()));
            psInsert.setString(5, dStatusTx.getText());
            psInsert.executeUpdate();
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Added successfully", "This information is added to the database.");
            showDestination();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        DatabaseOperations.disconnectFromDB(connection, psInsert);
    }

    private void showDestination() {
        ObservableList<Destination> list = this.getDestinationList();
        this.dIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.dFromCol.setCellValueFactory(new PropertyValueFactory("from"));
        this.dToCol.setCellValueFactory(new PropertyValueFactory("to"));
        this.dStopCol.setCellValueFactory(new PropertyValueFactory("stop"));
        this.dDistCol.setCellValueFactory(new PropertyValueFactory("distance"));
        this.dStatusCol.setCellValueFactory(new PropertyValueFactory("status"));
        this.tvDestination.setItems(list);
    }

    private ObservableList<Destination> getDestinationList() {
        ObservableList<Destination> destinationList = FXCollections.observableArrayList();
        Connection conn = connectToDB();
        String query = "SELECT id, from_where, to_where, stop, distance, status from destination";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Destination destination = new Destination(rs.getInt("id"), rs.getString("from_where"), rs.getString("to_where"), rs.getString("stop"), rs.getInt("distance"), rs.getString("status"));
                destinationList.add(destination);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return destinationList;
    }

    public void ButtonDeleteDestination(ActionEvent actionEvent) {
        String query = "DELETE FROM destination WHERE id =" + this.dIdTx.getText() + "";
        this.executeQuery(query);
        FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Deleted successfully", "Vehicle information deleted from database.");
        this.showDestination();
    }

    public void ButtonUpdateDestination(ActionEvent actionEvent) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement psUpdate;
        try {
            psUpdate = connection.prepareStatement("UPDATE destination SET from_where  = ?, to_where = ?, stop = ?, distance = ?, status = ? WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        psUpdate.setString(1, dFromTx.getText());
        psUpdate.setString(2, dToTx.getText());
        psUpdate.setString(3, dStopTx.getText());
        psUpdate.setInt(4, Integer.parseInt(dDistTx.getText()));
        psUpdate.setString(5, dStatusTx.getText());
        psUpdate.setInt(6, Integer.parseInt(dIdTx.getText()));
        psUpdate.executeUpdate();
        this.showDestination();
        DatabaseOperations.disconnectFromDB(connection, psUpdate);
    }

    public void handleMouseClickDestination(MouseEvent mouseEvent) {
        Destination destination = (Destination) tvDestination.getSelectionModel().getSelectedItem();
        dIdTx.setText(String.valueOf(destination.getId()));
        dFromTx.setText(destination.getFrom());
        dToTx.setText(destination.getTo());
        dStopTx.setText(destination.getStop());
        dDistTx.setText(String.valueOf(destination.getDistance()));
        dStatusTx.setText(destination.getStatus());
    }

    public void buttonAddOrder(ActionEvent actionEvent) {
        PreparedStatement psInsert;
        Connection connection = connectToDB();
        try {
            psInsert = connection.prepareStatement("INSERT INTO orders (destination_id, cargo_id, assignee_id, status) VALUES (?,?,?,?)");
            psInsert.setInt(1, Integer.parseInt(oDesTx.getText()));
            psInsert.setInt(2, Integer.parseInt(oCargTx.getText()));
            psInsert.setInt(3, currUserId);
            psInsert.setString(4, String.valueOf(oStatCb.getValue()));
            psInsert.executeUpdate();
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Added successfully", "This information is added to the database.");
            showOrder();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        DatabaseOperations.disconnectFromDB(connection, psInsert);
    }

    public void ButtonDeleteOrder(ActionEvent actionEvent) {
        if (String.valueOf(this.oStatCb.getValue()) != "in progress") {
            String query = "DELETE FROM order WHERE id =" + this.oIdTx.getText() + "";
            this.executeQuery(query);
            FxUtils.alertMessage(Alert.AlertType.ERROR, "Message", "Vehicle assigned successfully", "Vehicle assigned successfully.");
            this.showOrder();
        } else {
            FxUtils.alertMessage(Alert.AlertType.CONFIRMATION, "Confirmation", "Success", "");
        }
    }

    public void ButtonUpdateOrder(ActionEvent actionEvent) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement psUpdate;
        try {
            psUpdate = connection.prepareStatement("UPDATE orders SET destination_id  = ?, cargo_id = ?, status = ? WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        psUpdate.setInt(1, Integer.parseInt(oDesTx.getText()));
        psUpdate.setInt(2, Integer.parseInt(oCargTx.getText()));
        psUpdate.setString(3, String.valueOf((oStatCb.getValue())));
        psUpdate.setInt(3, Integer.parseInt(oIdTx.getText()));
        psUpdate.executeUpdate();
        this.showOrder();
        DatabaseOperations.disconnectFromDB(connection, psUpdate);
    }

    public void handleMouseClickOrder(MouseEvent mouseEvent) {
        Order order = (Order) tvOrder.getSelectionModel().getSelectedItem();
        oIdTx.setText(String.valueOf(order.getId()));
        oDesTx.setText(String.valueOf(order.getDestination_id()));
        oCargTx.setText(String.valueOf(order.getCargo_id()));
        oStatCb.setValue(String.valueOf(order.getStatus()));
    }

    private void showOrder() {
        ObservableList<Order> list = this.getOrderList();
        this.oIdCol.setCellValueFactory(new PropertyValueFactory("id"));
        this.oDesIdCol.setCellValueFactory(new PropertyValueFactory("destination_id"));
        this.oCargIdCol.setCellValueFactory(new PropertyValueFactory("cargo_id"));
        this.oAssigneeIdCol.setCellValueFactory(new PropertyValueFactory("assignee_id"));
        this.oVehIdCol.setCellValueFactory(new PropertyValueFactory("vehicle_id"));
        this.oStatCol.setCellValueFactory(new PropertyValueFactory("status"));
        this.tvOrder.setItems(list);
    }

    private ObservableList<Order> getOrderList() {
        ObservableList<Order> orderList = FXCollections.observableArrayList();
        Connection conn = connectToDB();
        String query = "SELECT id, destination_id, cargo_id, assignee_id, vehicle_id, status from orders";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                Order order = new Order(rs.getInt("id"), rs.getInt("destination_id"), rs.getInt("cargo_id"), rs.getInt("assignee_id"), rs.getInt("vehicle_id"), rs.getString("status"));
                orderList.add(order);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return orderList;
    }

    public void ShowDriverOrder() throws SQLException {
        driverOrderId = checkIfAssigned();
        if (driverOrderId == 0) {
            currOrderID.setText("None");
            currOrderStatus.setText("None");
        } else {
            currOrderID.setText(String.valueOf(driverOrderId));
            currOrderStatus.setText(checkIfAssignedStatus());
        }
    }

    public int checkIfAssigned() throws SQLException {
        Connection conn = connectToDB();
        String query = "SELECT * from orders where assignee_id = ? and status = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        try {
            ps.setInt(1, currUser.getUser_id());
            ps.setString(2, "in progress");
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt("id");
            rs.close();
            ps.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        DatabaseOperations.disconnectFromDB(conn, ps);
        return 0;
    }

    public String checkIfAssignedStatus() throws SQLException {
        Connection conn = connectToDB();
        String query = "SELECT * from orders where assignee_id = ? and status = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        try {
            ps.setInt(1, currUser.getUser_id());
            ps.setString(2, "in progress");
            ps.executeQuery();
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("status");
            rs.close();
            ps.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        DatabaseOperations.disconnectFromDB(conn, ps);
        return "none";
    }

    public void DoneOrder(ActionEvent actionEvent) throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement psUpdate;
        try {
            psUpdate = connection.prepareStatement("UPDATE orders SET status = ? WHERE id = ?");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        psUpdate.setString(1, "completed");
        psUpdate.setInt(2, driverOrderId);
        psUpdate.executeUpdate();
        currOrderStatus.setText("completed");
        DatabaseOperations.disconnectFromDB(connection, psUpdate);
    }

    public void updateAssignOrder() throws SQLException {
        orderIdOrder.getItems().clear();
        vehicleIdOrder.getItems().clear();
        AssigneeIdOrder.getItems().clear();

        Connection connection = connectToDB();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders WHERE status = ?");
        ps.setString(1, "waiting");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            orderIdOrder.getItems().add(rs.getString("id"));
        }

        ps = connection.prepareStatement("SELECT * FROM vehicles WHERE state = ?");
        ps.setString(1, "not selected");
        rs = ps.executeQuery();
        while (rs.next()) {
            vehicleIdOrder.getItems().add(rs.getInt("id"));
        }

        ps = connection.prepareStatement("SELECT * FROM users WHERE user_types_id = ?");
        ps.setInt(1, 1);
        rs = ps.executeQuery();
        while (rs.next()) {
            AssigneeIdOrder.getItems().add(rs.getString("user_id"));
        }
    }

    public void AssignOrder(ActionEvent actionEvent) throws SQLException {
            if (orderIdOrder.getValue().toString().isBlank() || vehicleIdOrder.getValue().toString().isBlank() || AssigneeIdOrder.getValue().toString().isBlank())
                return;
            assignDriver(Integer.parseInt(orderIdOrder.getValue().toString()), Integer.parseInt(vehicleIdOrder.getValue().toString()), Integer.parseInt(AssigneeIdOrder.getValue().toString()));
//            updateCurrUser();
            showOrder();
            showVehicle();
    }

    public void refreshOrder(ActionEvent actionEvent) throws SQLException {
        updateAssignOrder();
    }

    public void assignDriver(int orderId, int licensePlate, int driverId) throws SQLException {

        Connection connection = connectToDB();

        PreparedStatement ps = connection.prepareStatement("UPDATE orders SET vehicle_id=? WHERE id=?");
        ps.setInt(1, licensePlate);
        ps.setInt(2, orderId);
        ps.executeUpdate();

        ps = connection.prepareStatement("UPDATE orders SET assignee_id=? WHERE id=?");
        ps.setInt(1, driverId);
        ps.setInt(2, orderId);
        ps.executeUpdate();

        ps = connection.prepareStatement("UPDATE orders SET status=? WHERE id=?");
        ps.setString(1, "in progress");
        ps.setInt(2, orderId);
        ps.executeUpdate();

        ps = connection.prepareStatement("UPDATE vehicles SET state=? WHERE id=?");
        ps.setString(1, "selected");
        ps.setInt(2, licensePlate);
        ps.executeUpdate();
    }

    public void ordersBarChart() throws SQLException {
        Connection connection = connectToDB();
        PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS suma FROM orders WHERE status = ?");
        ps.setString(1, "completed");
        ResultSet rs = ps.executeQuery();
        rs.next();
        Integer completed = rs.getInt("suma");

        ps.setString(1, "in progress");
        rs = ps.executeQuery();
        rs.next();
        Integer progress = rs.getInt("suma");

        ps.setString(1, "waiting");
        rs = ps.executeQuery();
        rs.next();
        Integer waiting = rs.getInt("suma");

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Orders status count");
        xAxis.setLabel("Status");
        yAxis.setLabel("Count");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("2023");
        series1.getData().add(new XYChart.Data("Completed", completed));
        series1.getData().add(new XYChart.Data("In progress", progress));
        series1.getData().add(new XYChart.Data("Waiting", waiting));

        barChart.getData().clear();
        barChart.getData().addAll(series1);
    }
}