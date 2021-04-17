package controller.insert;

import init.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DatabaseManager;

import java.net.URL;
import java.util.ResourceBundle;


public class GoodsInsertController implements InsertController, Initializable {
    private DatabaseManager manager;
    private ChangeListener listener;
    private ObservableStringValue name = new SimpleStringProperty("");
    private InsertMode insertMode = InsertMode.insert;
    private String item;

    @Override
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
        label.setText("Update");

        String name = DatabaseManager.getSubstring(" NAME=", "NAME=", item);
        nameField.setText(name);
    }

    @FXML
    private Button insertButton;

    @FXML
    private TextField nameField;

    @FXML
    private Label label;

    @FXML
    private void insertButtonTapped() {
        if (nameField.getText().isEmpty()) {
            showAlert("Name id empty!", "Enter new good's name");
        } else {
            name = new SimpleStringProperty(nameField.getText());

            if (insertMode == InsertMode.insert) {
                manager.insertGood(nameField.getText());
            } else {
                String id = DatabaseManager.getIdFrom(item);
                manager.updateGood(id, nameField.getText());
            }

            listener.changed(name, "", name);
            Stage stage = (Stage) insertButton.getScene().getWindow();
            stage.close();
        }
    }
}
