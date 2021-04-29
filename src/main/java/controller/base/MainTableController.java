package controller.base;

import init.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utils.Connection;
import utils.DatabaseManager;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public final static String FXML = "/windows/main_window.fxml";
    public final static String SEL_FXML = "/windows/choice_window.fxml";

    private final Connection connection;
    private final DatabaseManager manager;
    public static final ObservableList data =
            FXCollections.observableArrayList();

    public MainController() {
        connection = Main.getConnection();
        manager = new DatabaseManager(connection);

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResourceAsStream(SEL_FXML));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private ListView<String> tableListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableListView.setItems(data);
        configureTableView();

        tableListView.setOnMouseClicked(event -> {
            System.out.println("clicked on " + tableListView.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setTitle(tableListView.getSelectionModel().getSelectedItem());

            FXMLLoader loader = new FXMLLoader();
            Parent root = null;

            try {
                root = loader.load(getClass().getResourceAsStream("/windows/table_window.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            TableController tableController = loader.getController();
            String parameter = tableListView.getSelectionModel().getSelectedItem();
            tableController.setParameter(parameter);
            try {
                tableController.loadData();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    @FXML
    private void createButtonTapped() {
        manager.createDatabase();
        configureTableView();
    }

    private void configureTableView() {
        try {
            data.clear();
            ResultSet set = connection.executeQueryAndGetResult("select table_name from user_tables");

            if (set != null) {
                while (set.next()) {
                    String name = set.getString(1);
                    data.add(name);
                }
            }

            tableListView.refresh();
        }
        catch(SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
