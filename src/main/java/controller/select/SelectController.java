package controller.select;

import controller.base.SelectTableController;
import init.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import utils.Connection;
import utils.Selections;

import java.io.IOException;
import java.sql.ResultSet;

public interface SelectController {

    final Connection connection = Main.getConnection();

    default void showAlert(String message, String comment) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Внимание");
        alert.setHeaderText(message);
        alert.setContentText(comment);
        alert.showAndWait();
    }

    default void showResult(String sql) {
        try {
            ResultSet set = connection.executeQueryAndGetResult(sql);

            if (set != null) {
                Stage stage = new Stage();

                FXMLLoader loader = new FXMLLoader();
                Parent root = null;

                try {
                    root = loader.load(getClass().getResourceAsStream(SelectTableController.fxml));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                SelectTableController tableController = loader.getController();
                tableController.set(set);
                assert root != null;
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert("Пустой результат", "Нет данных с вашими параметрами");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}