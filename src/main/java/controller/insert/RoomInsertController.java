package controller.insert;

import init.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import utils.Connection;
import utils.DatabaseManager;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomInsertController implements InsertController, Initializable {

    private DatabaseManager manager;
    private ChangeListener listener;
    private ObservableStringValue name = new SimpleStringProperty("");
    private ObservableList<String> items = FXCollections.<String>observableArrayList();
    private Map<String, Integer> tradeTypes;
    private InsertMode insertMode = InsertMode.insert;
    private String item;

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);

        try {
            ResultSet set = connection.executeQueryAndGetResult("select * from Trade_points");
            tradeTypes = new HashMap<>();
            items.clear();
            if (set != null) {
                while (set.next()) {
                    int id = set.getInt(1);
                    String name = set.getString(3);
                    tradeTypes.put(name, id);
                    items.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        pointChoose.setItems(items);
    }

    public void setMode(InsertMode mode) {
        insertMode = mode;
    }

    public void setItem(String item) {
        this.item = item;
        insertButton.setText("Update");
    }

    @FXML
    private ChoiceBox pointChoose;

    @FXML
    private Button insertButton;

    @FXML
    private void insertButtonTapped() {
        if (pointChoose.getValue().toString().isEmpty()) {
            showAlert("Empty field!", "Fill in required fields");
        } else {
            name = new SimpleStringProperty(pointChoose.getValue().toString());
            int point = tradeTypes.get(pointChoose.getValue().toString());
            manager.insertTradeRoom(point);
            listener.changed(name, "", name);
        }
    }

}
