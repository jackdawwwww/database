package controller.roles;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SellerMain {

    public Button button;
    private String windowName;

    /// Новая продажа
    public void newSale(ActionEvent actionEvent) {
    }

    /// Список продаж
    public void getSalesList(ActionEvent actionEvent) {
    }

    /// Учет товаров по торговым точкам
    public void getAccountingSelect(ActionEvent actionEvent) {
    }

    private void showSelectWindow() {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = loader.load(getClass().getResourceAsStream(windowName));
            primaryStage.setScene(new Scene(root));
        } catch (IOException ignored) {

        }

        primaryStage.show();
    }
}
