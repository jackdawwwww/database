package controller.roles;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProviderMain {

    public Button button;
    private String windowName;

    // Просмотр активных заявок
    public void getActive(ActionEvent actionEvent) {
    }

    // История заявок
    public void getStory(ActionEvent actionEvent) {
    }

    // Наши товары
    public void getGoods(ActionEvent actionEvent) {
    }

    // Наши поставки
    public void getDel(ActionEvent actionEvent) {
    }

    private void showSelectWindow() {
        Stage primaryStage = (Stage) button.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = loader.load(getClass().getResourceAsStream(windowName));
            primaryStage.setScene(new Scene(root));
        } catch (IOException ignored) {

        }
    }
}
