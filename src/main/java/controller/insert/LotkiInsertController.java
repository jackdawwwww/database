package controller.insert;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DatabaseManager;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LotkiInsertController implements InsertController, Initializable {

    private DatabaseManager manager;
    private ChangeListener listener;
    private ObservableStringValue name = new SimpleStringProperty("");
    private ObservableList<String> items = FXCollections.<String>observableArrayList();
    private InsertMode insertMode = InsertMode.insert;
    private String item;

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
    }

    public void setMode(InsertMode mode) {
        insertMode = mode;
    }

    public void setItem(String item) {
        this.item = item;
        insertButton.setText("Изменить");

        String type = DatabaseManager.getSubstring(" TYPE=", "TYPE=", item);
        String name = DatabaseManager.getSubstring(" NAME=", "NAME=", item);

        nameField.setText(name);
    }

    @FXML
    private TextField nameField;

    @FXML
    private Button insertButton;

    @FXML
    private void insertButtonTapped() {
        if (nameField.getText().isEmpty()) {
            showAlert("Fields: 'name' are required!","Fill in required fields");
        } else {
            name = new SimpleStringProperty(nameField.getText());
            String type = "1";
            if (insertMode == InsertMode.insert) {
                //manager.insertTradePoint(nameField.getText(), type, size, rent, communal, counters);
            } else {
                String id = DatabaseManager.getIdFrom(item);
                //manager.updateTradePoint(id, type, nameField.getText(), size, rent, communal, counters);
            }

            listener.changed(name, "", name);
            Stage stage = (Stage) insertButton.getScene().getWindow();
            stage.close();
        }
    }

}
