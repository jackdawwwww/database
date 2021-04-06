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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import utils.DatabaseManager;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PointsInsertController implements InsertController, Initializable {

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
        TextField[] fields = { pointSize, rentPrice, communalPayments, countersNum };

        for(TextField field: fields) {
            field.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue,
                                    String newValue) {
                    if (!newValue.matches("\\d*")) {
                        field.setText(newValue.replaceAll("[^\\d]", ""));
                    }
                }
            });
        }

        try {
            ResultSet set = connection.executeQueryAndGetResult("select * from Trade_types");
            tradeTypes = new HashMap<>();
            items.clear();
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(2);
                    Integer id = set.getInt(1);
                    tradeTypes.put(name, id);
                    items.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        typeChoice.setItems(items);
    }

    public void setMode(InsertMode mode) {
        insertMode = mode;
    }

    public void setItem(String item) {
        this.item = item;
        insertButton.setText("Update");
    }

    @FXML
    private ChoiceBox typeChoice;

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
        if (nameField.getText().isEmpty() || typeChoice.getValue().toString().isEmpty() || pointSize.getText().isEmpty()) {
            showAlert("Fields: 'name', 'trade type' and 'point size' are required!","Fill in required fields");
        } else {
            name = new SimpleStringProperty(nameField.getText());
            int type = tradeTypes.get(typeChoice.getValue().toString());
            int size = Integer.parseInt(pointSize.getText());
            int rent = rentPrice.getText().isEmpty() ? 0 : Integer.parseInt(rentPrice.getText());
            int communal = communalPayments.getText().isEmpty() ? 0 : Integer.parseInt(communalPayments.getText());
            int counters = countersNum.getText().isEmpty() ? 0 : Integer.parseInt(countersNum.getText());

            manager.insertTradePoint(nameField.getText(), type, size, rent, communal, counters);

            TextField[] fields = { nameField, pointSize, rentPrice, communalPayments, countersNum };
            for(TextField field: fields) {
                field.setText("");
            }
            listener.changed(name, "", name);
        }
    }

}
