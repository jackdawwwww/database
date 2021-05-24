package controller.insert;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.DatabaseManager;

import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RequestInsertController implements InsertController, Initializable {

    public Button button;
    public TextField countField;
    public ChoiceBox chooseGood;
    public ChoiceBox chooseTradePoint;
    public ChoiceBox providerBox;
    public Label provLabel;
    public CheckBox checkBox;

    private DatabaseManager manager;
    private ChangeListener listener;
    private ObservableStringValue good = new SimpleStringProperty("");
    private ObservableList<String> items1 = FXCollections.<String>observableArrayList();
    private Map<String, Integer> tradeTypes;
    private String item;

    private ObservableList<String> items2 = FXCollections.<String>observableArrayList();
    private Map<String, Integer> goods;

    private ObservableList<String> items3 = FXCollections.<String>observableArrayList();
    private Map<String, Integer> providers;

    private InsertMode insertMode = InsertMode.insert;
    private Boolean canChooseProvider = true, canChooseStatus = true;

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public void setCanChooseProvider(Boolean canChooseProvider) {
        this.canChooseProvider = canChooseProvider;
        if (!canChooseProvider) {
            provLabel.setVisible(false);
            providerBox.setVisible(false);
        } else {
            provLabel.setVisible(true);
            providerBox.setVisible(true);
            checkBox.setVisible(true);
        }
    }

    public void setCanChooseStatus(Boolean canChooseStatus) {
        this.canChooseStatus = canChooseStatus;
        if (!canChooseStatus) {
            checkBox.setVisible(false);
        } else {
            checkBox.setVisible(true);
            chooseGood.setDisable(true);
            chooseTradePoint.setDisable(true);
            countField.setDisable(true);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
        TextField[] fields = { countField };

        for(TextField field: fields) {
            field.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.matches("\\d*")) {
                    field.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });
        }

        try {
            ResultSet set1 = connection.executeQueryAndGetResult("select * from Trade_points");
            tradeTypes = new HashMap<>();
            items1.clear();
            if (set1 != null) {
                while (set1.next()) {
                    String name = set1.getString(3);
                    Integer id = set1.getInt(1);
                    tradeTypes.put(name, id);
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

            ResultSet set3 = connection.executeQueryAndGetResult("select * from Providers");
            providers = new HashMap<>();
            items3.clear();
            if (set3 != null) {
                while (set3.next()) {
                    String name = set3.getString(2);
                    Integer id = set3.getInt(1);
                    providers.put(name, id);
                    items3.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        chooseTradePoint.setItems(items1);
        chooseGood.setItems(items2);
        providerBox.setItems(items3);
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
        String provider = DatabaseManager.getSubstring(" PROVIDER=", "PROVIDER=", item);
        Boolean status = Boolean.parseBoolean(DatabaseManager.getSubstring(" STATUS=", "STATUS=", item));

        chooseTradePoint.setValue(items1.get(Integer.parseInt(point)-1));
        chooseGood.setValue(items2.get(Integer.parseInt(good)-1));

        if (!provider.isEmpty()) {
            providerBox.setValue(items3.get(Integer.parseInt(provider)-1));
        }
        chooseGood.setValue(items2.get(Integer.parseInt(good)-1));
        countField.setText(count);
        checkBox.setSelected(status);
    }

    @FXML
    private void buttonAction() {
        if (canChooseProvider) {
            if (providerBox.getValue().toString().isEmpty() || providerBox == null) {
                showAlert("Fill in required fields","Fill in required fields");
            }
        }
        if (chooseTradePoint.getValue().toString().isEmpty() || chooseGood.getValue().toString().isEmpty() || countField.getText().isEmpty()) {
            showAlert("All fields are required!","Fill in required fields");
        } else {
            good = new SimpleStringProperty(goods.get(chooseGood.getValue().toString()).toString());
            String count = countField.getText();
            String point = tradeTypes.get(chooseTradePoint.getValue().toString()).toString();
            String provider = "";
            String status = checkBox.selectedProperty().getValue() ? "1" : "0";

            if (canChooseProvider || (!providers.isEmpty() && canChooseStatus)) {
                provider = providers.get(providerBox.getValue().toString()).toString();
            }

            if (insertMode == InsertMode.insert) {
                manager.insertRequest(point, good.getValue(), provider, count, status);
            } else {
                String id = DatabaseManager.getIdFrom(item);
                manager.updateRequest(id, point, good.getValue(), provider, count, status);
            }

            if (listener != null) {
                listener.changed(good, "", good);
            }
            Stage stage = (Stage) button.getScene().getWindow();
            stage.close();
        }
    }

}
