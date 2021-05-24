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

public class AccountingInsertController implements InsertController, Initializable {

    public Button button;
    public TextField priceField;
    public TextField countField;
    public ChoiceBox chooseGood;
    public ChoiceBox chooseTradePoint;

    private DatabaseManager manager;
    private ChangeListener listener;
    private ObservableStringValue good = new SimpleStringProperty("");
    private ObservableList<String> items1 = FXCollections.<String>observableArrayList();
    private Map<String, Integer> tradePoints;
    private String item;

    private ObservableList<String> items2 = FXCollections.<String>observableArrayList();
    private Map<String, Integer> goods;
    private InsertMode insertMode = InsertMode.insert;

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
        TextField[] fields = { countField, priceField};

        for(TextField field: fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }

        try {
            ResultSet set1 = connection.executeQueryAndGetResult("select * from Trade_points");
            tradePoints = new HashMap<>();
            items1.clear();
            if (set1 != null) {
                while (set1.next()) {
                    String name = set1.getString(3);
                    Integer id = set1.getInt(1);
                    tradePoints.put(name, id);
                    items1.add(name);
                }
            }

            ResultSet set2 = connection.executeQueryAndGetResult("select * from Goods");
            goods = new HashMap<>();
            items2.clear();
            if (set2 != null) {
                while (set2.next()) {
                    String name = set2.getString(2);
                    Integer id = set2.getInt(1);
                    goods.put(name, id);
                    items2.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        chooseTradePoint.setItems(items1);
        chooseGood.setItems(items2);
    }

    public void setMode(InsertMode mode) {
        insertMode = mode;
    }

    public void setItem(String item) {
        this.item = item;
        button.setText("Изменить");

        String point = DatabaseManager.getSubstring(" TRADE_POINT=", "TRADE_POINT=", item);
        String good = DatabaseManager.getSubstring(" GOOD=", "GOOD=", item);
        String count = DatabaseManager.getSubstring(" COUNT=", "COUNT=", item);
        String price = DatabaseManager.getSubstring(" PRICE=", "PRICE=", item);

        chooseTradePoint.setValue(items1.get(Integer.parseInt(point)-1));
        chooseGood.setValue(items2.get(Integer.parseInt(good)-1));
        countField.setText(count);
        priceField.setText(price);
    }

    @FXML
    private void buttonAction() {
        if (priceField.getText().isEmpty() || chooseTradePoint.getValue().toString().isEmpty() || chooseGood.getValue().toString().isEmpty() || countField.getText().isEmpty()) {
            showAlert("All fields are required!","Fill in required fields");
        } else {
            good = new SimpleStringProperty(goods.get(chooseGood.getValue().toString()).toString());
            String count = countField.getText();
            String price = priceField.getText();
            String point = tradePoints.get(chooseTradePoint.getValue().toString()).toString();

            if (insertMode == InsertMode.insert) {
                manager.insertAccounting(point, good.getValue(), count, price);
            } else {
                String id = DatabaseManager.getIdFrom(item);
                manager.updateAccounting(id, point, good.getValue(), count, price);
            }

            listener.changed(good, "", good);
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }

}
