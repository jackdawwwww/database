package controller;

import init.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import utils.Connection;
import utils.DatabaseManager;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainController extends BaseController implements Initializable {
    public final static String URL_FXML = "/main_window.fxml";
    private Connection connection;
    private DatabaseManager manager;

    public MainController() {
        connection = Main.getConnection();
        manager = new DatabaseManager(connection);
    }

    @FXML
    private ListView<String> tableListView;

    @FXML
    private Button createDB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureTableView();
    }

    @FXML
    private void createButtonTapped() throws SQLException {
        manager.createDatabase();
        configureTableView();
    }

    private void configureTableView() {
        try {
            ResultSet set = connection.executeQueryAndGetResult("select table_name from user_tables");

            if (set != null) {
                while (set.next()) {
                    String name = set.getString(1);
                    if (!tableListView.getItems().contains(name)) {
                        tableListView.getItems().add(name);
                    }
                }
            }
        }
        catch(SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
