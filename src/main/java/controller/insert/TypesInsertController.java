package controller.insert;

import init.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Connection;
import utils.DatabaseManager;

import java.net.URL;
import java.util.ResourceBundle;

public class TypesInsertController implements InsertController, Initializable {
    private DatabaseManager manager;
    private ChangeListener listener;
    private ObservableStringValue name = new SimpleStringProperty("");
    private InsertMode insertMode = InsertMode.insert;
    private String item;

    private final Connection connection = Main.getConnection();

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
        insertButton.setText("Update");

        String start = " NAME=";
        int substringStartIndex = item.indexOf(start);
        if(substringStartIndex < 0) {
            start = "NAME=";
            substringStartIndex = item.indexOf(start);
        }
        int endIndex = item.indexOf(',', substringStartIndex);
        if(endIndex < 0) {
            endIndex = item.indexOf('}', substringStartIndex);
        }
        String name = item.substring(substringStartIndex + start.length(), endIndex);
        nameField.setText(name);
    }

    @FXML
    private Button insertButton;

    @FXML
    private TextField nameField;

    @FXML
    private void insertButtonTapped() {
        if (nameField.getText().isEmpty()) {
            showAlert("Name id empty!", "Enter new trade type name");
        } else {
            name = new SimpleStringProperty(nameField.getText());

            if (insertMode == InsertMode.insert) {
                manager.insertTradeType(nameField.getText());
            } else {
                String id = DatabaseManager.getIdFrom(item);
                manager.updateTradeType(id, nameField.getText());
            }

            listener.changed(name, "", name);
            Stage stage = (Stage) insertButton.getScene().getWindow();
            stage.close();
        }
    }



}
