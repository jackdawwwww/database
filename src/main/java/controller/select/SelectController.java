package controller.select;

import init.Main;
import javafx.scene.control.Alert;
import utils.Connection;
import utils.Selections;

public interface SelectController {

    final Connection connection = Main.getConnection();

    default void showAlert(String message, String comment) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(comment);
        alert.showAndWait();
    }

}