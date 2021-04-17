package controller.base;

import init.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import utils.Connection;
import utils.Selections;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SelectTableController implements Initializable {
    public static String fxml = "/windows/select/select_result_window.fxml";
    private final LinkedList<TableColumn<Map, String>> columns = new LinkedList<>();
    private final ObservableList<Map<String, Object>> items = FXCollections.<Map<String, Object>>observableArrayList();
    private final LinkedList<String> columnNames = new LinkedList<>();
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
    private final SimpleDateFormat formatter2 = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
    private ResultSet set;

    @FXML
    private TableView tableView;

    public SelectTableController() {
    }

    public void set(ResultSet set) {
        this.set = set;

        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setItems(items);
    }

    public void loadData() throws SQLException {
        items.clear();
        columns.clear();

        ResultSetMetaData metaData = set.getMetaData();
        int columnSize = set.getMetaData().getColumnCount();
        try {
            for(int i = 1; i <= columnSize; i++) {
                String columnName = metaData.getColumnName(i);
                TableColumn<Map, String> column = new TableColumn<>(columnName);
                column.setCellValueFactory(new MapValueFactory<>(columnName));
                column.setMinWidth(50);
                columns.add(column);
                columnNames.add(columnName);
            }

            tableView.getColumns().setAll(columns);
            for(int i = 1; set.next(); ++i) {
                Map<String, Object> map = new HashMap<>();

                for(int j = 1; j <= columnSize; j++) {
                    String value = set.getString(j);

                    try {
                        Date date = formatter.parse(value);
                        value = formatter2.format(date);
                    } catch (ParseException ignore) {

                    }
                    map.put(columnNames.get(j-1), value);
                }
                items.add(map);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}