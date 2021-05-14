package controller.roles;

import controller.base.MainTableController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMain {

    public Button button;

    /// Таблицы и запросы
    public void getAll() {
        showSelectWindow(MainTableController.FXML);
    }

    /// Добавить нового пользователя
    public void createNew(ActionEvent actionEvent) {
    }

    private void showSelectWindow(String name) {
        Stage primaryStage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = loader.load(getClass().getResourceAsStream(name));
            primaryStage.setScene(new Scene(root));
        } catch (IOException ignored) {

        }
    }
}

