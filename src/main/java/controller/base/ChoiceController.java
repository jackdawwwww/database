package controller.base;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utils.Selections;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChoiceController implements Initializable {

    public static final ObservableList data =
            FXCollections.observableArrayList();

    public ChoiceController() {
    }

    @FXML
    private ListView<String> selectsListView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectsListView.setItems(data);
        configureTableView();

        selectsListView.setOnMouseClicked(event -> {
            System.out.println("clicked on " + selectsListView.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();

            FXMLLoader loader = new FXMLLoader();
            Parent root = null;
            Selections sel = Selections.getSelectionByName(selectsListView.getSelectionModel().getSelectedItem());

            try {
                root = loader.load(getClass().getResourceAsStream(sel.getWindowName()));
            } catch (IOException e) {
                e.printStackTrace();
           }

            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();
        });
    }

    private void configureTableView() {
        data.clear();
        Selections[] possibleValues = Selections.sellers.getDeclaringClass().getEnumConstants();

        for(Selections value:  possibleValues) {
            String name = value.getName();
            data.add(name);
        }
    }
}
