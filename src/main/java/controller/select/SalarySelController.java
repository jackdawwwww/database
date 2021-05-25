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

public class SalarySelController implements SelectController, Initializable {
    public Button listButton;
    public ChoiceBox choiceBox;
    public ChoiceBox choosePoint;

    private DatabaseManager manager;

    private ObservableList<String> items1 = FXCollections.<String>observableArrayList();
    private ObservableList<String> items2 = FXCollections.<String>observableArrayList();

    private Map<String, Integer> types;
    private Map<String, Integer> points;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
        choiceBox.setItems(items1);
        choosePoint.setItems(items2);

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
            ResultSet set = connection.executeQueryAndGetResult("select * from trade_points");
            points = new HashMap<>();
            items2.clear();
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(3);
                    Integer id = set.getInt(1);
                    points.put(name, id);
                    items2.add(name);
                }
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void getButtonTapped() {
        String sql = "select tp.name as point, sl.name as seller, sl.SALARY from SELLERS sl " +
                "inner join TRADE_POINTS TP on TP.ID = sl.trade_point " + getParameters();

        showResult(sql);
    }

    private String getParameters() {
        String type_id = !choiceBox.getSelectionModel().isEmpty() && !choiceBox.getValue().toString().equals("all") ? types.get(choiceBox.getValue().toString()).toString() : "";
        String point_id = !choosePoint.getSelectionModel().isEmpty() && !choosePoint.getValue().toString().equals("all") ? points.get(choosePoint.getValue().toString()).toString() : "";

        String parameters = "";
        if (!type_id.isEmpty()) {
            parameters = " inner join TRADE_TYPES TPT on TPT.ID = TP.type where tpt.id = " + type_id;

            if (!point_id.isEmpty()) {
                parameters += " and tp.id = " + point_id;
            }
        } else {
            if (!point_id.isEmpty()) {
                parameters = "where tp.id = " + point_id;
            }
        }

        return parameters;
    }


}
