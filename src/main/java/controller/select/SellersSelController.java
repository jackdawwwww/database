package controller.select;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import utils.DatabaseManager;

import java.net.URL;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SellersSelController implements SelectController, Initializable {
    public Button listButton;
    public ChoiceBox choiceBox;
    public DatePicker startPicker;
    public DatePicker endPicker;

    private DatabaseManager manager;

    private ObservableList<String> items = FXCollections.<String>observableArrayList();
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    private Map<String, Integer> types;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
        choiceBox.setItems(items);

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
            ResultSet set = connection.executeQueryAndGetResult("select * from trade_types");
            types = new HashMap<>();
            items.clear();
            items.add("all");
            types.put("all", 0);
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(2);
                    Integer id = set.getInt(1);
                    types.put(name, id);
                    items.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void getButtonTapped() {
        String sql = "WITH t1 AS  (SELECT seller, COUNT (*) as count FROM Sales " +
        "where sales.sel_date between " + getDateParameters() + "GROUP BY seller)" +

        "SELECT sellers.id, Sellers.name, COALESCE (count, 0) as count FROM Sellers LEFT JOIN t1 ON Sellers.id=t1.seller " +
        "INNER JOIN Trade_points on sellers.trade_point=trade_points.id " + getParameters();

        //getParameters();
        showResult(sql);
    }

    private String getDateParameters() {
        String start = startPicker.getValue() != null ? startPicker.getValue().toString() : formatter.format(new Date(0L));
        String end = endPicker.getValue() != null ? endPicker.getValue().toString() : formatter.format(new Date(System.currentTimeMillis()));

        String parameters = "'" + start +
                "' and '" + end + "' ";
        return parameters;
    }

    private String getParameters() {
        String type_id = !choiceBox.getSelectionModel().isEmpty() && !choiceBox.getValue().toString().equals("all") ? types.get(choiceBox.getValue().toString()).toString() : "";

        String parameters = "";
        if (!type_id.isEmpty()) {
            parameters = "where trade_points.type = " + type_id;
        }

        return parameters;
    }


}
