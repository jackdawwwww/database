package controller.select;

import controller.base.SelectTableController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import utils.DatabaseManager;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class NomenclatureSelController implements SelectController, Initializable {
    public ChoiceBox chooseBox;

    private DatabaseManager manager;

    private ObservableList<String> items = FXCollections.<String>observableArrayList();
    private Map<String, Integer> points;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        manager = new DatabaseManager(connection);
        chooseBox.setItems(items);

        try {
            ResultSet set = connection.executeQueryAndGetResult("select * from trade_points");
            points = new HashMap<>();
            items.clear();
            if (set != null) {
                while (set.next()) {
                    String name = set.getString(3);
                    Integer id = set.getInt(2);
                    points.put(name, id);
                    items.add(name);
                }

                chooseBox.setValue(items.stream().findFirst());
            }
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public void buttonTapped() {
        if (chooseBox.getSelectionModel().isEmpty()) {
            showAlert("Choose trade point!", "");
        } else {
            String sql = "WITH t1 AS (SELECT good, count, trade_point FROM accounting) SELECT Goods.id, Goods.name, COALESCE (count, 0) as count FROM Goods " +
                    "LEFT JOIN t1 ON Goods.id=t1.good where t1.trade_point = " + points.get(chooseBox.getValue().toString()) + " order by goods.id";

            showResult(sql);
        }
    }
}
