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

public class KioskiInsertController implements InsertController, Initializable {

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
        TextField[] fields = { pointSize, rentPrice, communalPayments, countersNum };

        for(TextField field: fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }

    }

    public void setMode(InsertMode mode) {
        insertMode = mode;
    }

    public void setItem(String item) {
        this.item = item;
        insertButton.setText("Изменить");

        String type = DatabaseManager.getSubstring(" TYPE=", "TYPE=", item);
        String name = DatabaseManager.getSubstring(" NAME=", "NAME=", item);
        String size = DatabaseManager.getSubstring(" POINT_SIZE=", "POINT_SIZE=", item);
        String price = DatabaseManager.getSubstring(" RENT_PRICE=", "RENT_PRICE=", item);
        String payments = DatabaseManager.getSubstring(" COMMUNAL_PAYMENTS=", "COMMUNAL_PAYMENTS=", item);
        String counters = DatabaseManager.getSubstring(" COUNTERS_NUM=", "COUNTERS_NUM=", item);

        nameField.setText(name);
        pointSize.setText(size);
        rentPrice.setText(price);
        communalPayments.setText(payments);
        countersNum.setText(counters);
    }

    @FXML
    private TextField nameField;

    @FXML
    private TextField pointSize;

    @FXML
    private TextField rentPrice;

    @FXML
    private TextField communalPayments;

    @FXML
    private TextField countersNum;

    @FXML
    private Button insertButton;

    @FXML
    private void insertButtonTapped() {
        if (nameField.getText().isEmpty() || pointSize.getText().isEmpty()) {
            showAlert("Fields: 'name' and 'point size' are required!","Fill in required fields");
        } else {
            name = new SimpleStringProperty(nameField.getText());
            String type = "2";
            String size = pointSize.getText();
            String rent = rentPrice.getText().isEmpty() ? "null" : rentPrice.getText();
            String communal = communalPayments.getText().isEmpty() ? "null" : communalPayments.getText();
            String counters = countersNum.getText().isEmpty() ? "null" : countersNum.getText();
            if (insertMode == InsertMode.insert) {
            //    manager.insertTradePoint(nameField.getText(), type, size, rent, communal, counters);
            } else {
                String id = DatabaseManager.getIdFrom(item);
            //    manager.updateTradePoint(id, type, nameField.getText(), size, rent, communal, counters);
            }

            listener.changed(name, "", name);
            Stage stage = (Stage) insertButton.getScene().getWindow();
            stage.close();
        }
    }

}
