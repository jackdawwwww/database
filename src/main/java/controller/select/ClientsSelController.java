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

public class ClientsSelController implements SelectController, Initializable {
    public Button listButton;
    public ChoiceBox choiceBox;
    public DatePicker startPicker;
    public DatePicker endPicker;
    public ChoiceBox choosePoint;
    public ChoiceBox chooseGood;

    private DatabaseManager manager;

    private final SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
    private ObservableList<String> items1 = FXCollections.<String>observableArrayList();
    private ObservableList<String> items2 = FXCollections.<String>observableArrayList();
    private ObservableList<String> items3 = FXCollections.<String>observableArrayList();

    private Map<String, Integer> types;
    private Map<String, Integer> points;
    private Map<String, Integer> goods;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
        choiceBox.setItems(items1);
        chooseGood.setItems(items2);
        choosePoint.setItems(items3);

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
            items1.clear();
            items1.add("all");
            types.put("all", 0);
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(2);
                    Integer id = set.getInt(1);
                    types.put(name, id);
                    items1.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet set = connection.executeQueryAndGetResult("select * from goods");
            goods = new HashMap<>();
            items2.clear();
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(2);
                    Integer id = set.getInt(1);
                    goods.put(name, id);
                    items2.add(name);
                }
                chooseGood.setValue(items2.stream().findFirst());
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        try {
            ResultSet set = connection.executeQueryAndGetResult("select * from trade_points");
            points = new HashMap<>();
            items3.clear();
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(3);
                    Integer id = set.getInt(1);
                    points.put(name, id);
                    items3.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void getButtonTapped() {
        if (chooseGood.getSelectionModel().isEmpty()) {
            showAlert("Choose good type!", "Field is required!");
            return;
        }

        String sql = "select unique tp.name as point, cus.NAME as customer, pr.name as good, pur.sel_date " +
                "from TRADE_POINTS tp " +
                "         inner join SELLERS S on tp.ID = S.trade_point " +
                "         inner join SALES pur on S.ID = pur.seller " +
                "         inner join Customers cus on pur.Customer = cus.ID " +
                "         inner join GOODS pr on pur.good = pr.ID " + getParameters() + " and pur.sel_date between " + getDateParameters();


        showResult(sql);
    }

    private String getDateParameters() {
        String pattern = "dd-MMM-yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        String start = startPicker.getValue() != null ? startPicker.getValue().format(dateFormatter) : formatter.format(new Date(0L));
        String end = endPicker.getValue() != null ? endPicker.getValue().format(dateFormatter) : formatter.format(new Date(System.currentTimeMillis()));

        String parameters = "'" + start +
                "' and '" + end + "' ";
        return parameters;
    }

    private String getParameters() {
        String type_id = !choiceBox.getSelectionModel().isEmpty() && !choiceBox.getValue().toString().equals("all") ? types.get(choiceBox.getValue().toString()).toString() : "";
        String point_id = !choosePoint.getSelectionModel().isEmpty() && !choosePoint.getValue().toString().equals("all") ? points.get(choosePoint.getValue().toString()).toString() : "";
        String good_id = !chooseGood.getSelectionModel().isEmpty() ? goods.get(chooseGood.getValue().toString()).toString() : "";

        String parameters = "";
        if (!type_id.isEmpty()) {
            parameters = "inner join Trade_types tpt on tp.type = tpt.ID where tpt.id = " + type_id;

            if (!point_id.isEmpty()) {
                parameters += " and tp.id = " + point_id + " and pr.id = " + good_id;
            } else {
                parameters += " and pr.id = " + good_id;
            }
        } else {
            if (!point_id.isEmpty()) {
                parameters = "where tp.id = " + point_id + " and pr.id = " + good_id;
            } else {
                parameters = "where pr.id = " + good_id;
            }
        }

        return parameters;
    }


}
