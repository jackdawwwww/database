package controller.insert;

import init.Main;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Alert;
import utils.Connection;

public interface InsertController {

    final Connection connection = Main.getConnection();

    public default void setListener(ChangeListener listener) {

    }

    default void showAlert(String message, String comment) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.setContentText(comment);
        alert.showAndWait();
    }

    void setMode(InsertMode mode);
    void setItem(String item);
}