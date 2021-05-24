package controller.additional;

import controller.insert.InsertMode;
import controller.insert.RequestInsertController;
import init.Main;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.stage.Stage;
import utils.Connection;
import utils.DatabaseManager;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.ResourceBundle;

public class ProvReqTableController implements Initializable {

    public TableView tableView;
    public Button updateButton;
    private String sql;

    private final Connection connection = Main.getConnection();
    private final LinkedList<TableColumn<Map, String>> columns = new LinkedList<>();
    private final ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();
    private final LinkedList<String> columnNames = new LinkedList<>();

    public ProvReqTableController() {
    }

    @FXML
    private void removeButtonTapped() {
        Object itemToRemove = tableView.getSelectionModel().getSelectedItem();
        String item = itemToRemove.toString();
        String id = DatabaseManager.getIdFrom(item);
        connection.delete("DELETE FROM REQUESTS WHERE REQUESTS.ID LIKE " + id);
        tableView.getItems().removeAll(itemToRemove);
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    private void updateButtonTapped() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            configureWindow(InsertMode.update);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(items);
    }

    public void loadData() throws SQLException {
        items.clear();
        columns.clear();

        ResultSet set = connection.executeQueryAndGetResult(sql);
        ResultSetMetaData metaData = set.getMetaData();
        int columnSize = set.getMetaData().getColumnCount();
        try {
            for (int i = 1; i <= columnSize; i++) {
                String columnName = metaData.getColumnName(i);
                TableColumn<Map, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(new MapValueFactory<>(columnName));
                column.setMinWidth(50);
                columns.add(column);
                columnNames.add(columnName);
            }

            tableView.getColumns().setAll(columns);
            for (int i = 1; set.next(); ++i) {
                Map<String, Object> map = new HashMap<>();

                for (int j = 1; j <= columnSize; j++) {
                    String value = set.getString(j);

                    if (value == null) {
                        value = "";
                    }

                    map.put(columnNames.get(j - 1), value);

                }
                items.add(map);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void setSQL(String sql) {
        this.sql = sql;
        System.out.println("sql: " + sql);
        try {
            loadData();
        } catch (SQLException ignored) {

        }
    }

    private void configureWindow(InsertMode mode) {
        String windowName = "/windows/insertWindows/request_insert_window.fxml";
        ChangeListener listener = (observable, oldValue, newValue) -> {
            try {
                loadData();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        };

        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream(windowName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestInsertController controller = loader.getController();
        controller.setListener(listener);
        controller.setMode(mode);
        controller.setCanChooseProvider(false);
        controller.setCanChooseStatus(true);

        if (mode == InsertMode.update) {
            Object itemToUpdate = tableView.getSelectionModel().getSelectedItem();
            String item = itemToUpdate.toString();
            controller.setItem(item);
            stage.setTitle("Update requests");
        } else {
            stage.setTitle("Insert requests");
        }

        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

}

