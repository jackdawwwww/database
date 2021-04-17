package controller.select;

import controller.base.SelectTableController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import utils.DatabaseManager;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SuppliersSelController implements SelectController, Initializable {

    public Button listButton;
    public Button numButton;
    public ChoiceBox choiceBox;
    public TextField numberField;
    public DatePicker startPicker;
    public DatePicker endPicker;

    private DatabaseManager manager;

    private ObservableStringValue name = new SimpleStringProperty("");
    private ObservableList<String> items = FXCollections.<String>observableArrayList();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    private Map<String, Integer> goods;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
       for (DatePicker picker: new DatePicker[] { startPicker, endPicker }) {
           picker.setShowWeekNumbers(false);
           String pattern = "dd.MM.yyyy";
           picker.setConverter(new StringConverter<LocalDate>() {
               final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

               @Override
               public String toString(LocalDate date) {
                   if (date != null) {
                       return dateFormatter.format(date);
                   } else {
                       return "";
                   }
               }

               @Override
               public LocalDate fromString(String string) {
                   if (string != null && !string.isEmpty()) {
                       return LocalDate.parse(string, dateFormatter);
                   } else {
                       return null;
                   }
               }
           });
        }

        try {
            ResultSet set = connection.executeQueryAndGetResult("select * from goods");
            goods = new HashMap<>();
            items.clear();
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(2);
                    Integer id = set.getInt(1);
                    goods.put(name, id);
                    items.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void listButtonTapped(ActionEvent event) throws IOException {
        String sql = "SELECT Providers.name FROM providers INNER JOIN Deliveries ON Deliveries.provider_id = Providers.id" + getParameters();;
        try {
            ResultSet set = connection.executeQueryAndGetResult(sql);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResourceAsStream(SelectTableController.fxml));

            SelectTableController tableController = loader.getController();
            tableController.set(set);
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void numButtonTapped(ActionEvent event) {
        String sql = "SELECT count(Providers.id)  FROM providers INNER JOIN Deliveries ON Deliveries.provider_id = Providers.id" + getParameters();
        ResultSet set;
        try {
            set = connection.executeQueryAndGetResult(sql);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResourceAsStream(SelectTableController.fxml));

            SelectTableController tableController = loader.getController();
            tableController.set(set);
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getParameters() {

        String good_id = !choiceBox.getSelectionModel().isEmpty() ? goods.get(choiceBox.getValue().toString()).toString() : "";
        String count = numberField.getText();
        String start = startPicker.getValue() != null ? startPicker.getValue().toString() : formatter.format(new Date(0L));
        String end = endPicker.getValue() != null ? endPicker.getValue().toString() : formatter.format(new Date(System.currentTimeMillis()));

        if (count.isEmpty()) {
            count = "1";
        }

        String parameters = "";
        if (!good_id.isEmpty()) {
            parameters = " where Deliveries.good_id = " + good_id + " and Deliveries.count >= " + count;

            if(!start.isEmpty()) {
                parameters += " and deliveries.del_date >= '" + start + "' and deliveries.del_date <= '" + end + "'";
            }
        } else  {
            parameters = " where deliveries.del_date >= '" + start +
                    "' and deliveries.del_date <= '" + end + "'" + " and Deliveries.count >= " + count;
        }

        return parameters;
    }
}

