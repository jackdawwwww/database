package controller.roles;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Selections;

import java.io.IOException;

public class SellerManagerMain {

    public Button button;
    private String windowName;

    /// Сведения о наиболее активных покупателях
    public void getClientTop() {
    }

    /// Сведения о покупателях указанного товара
    public void getClientByGood() {
    }

    /// Сведения о покупателях указанного товара
    public void getStat() {
    }

    /// Данные о заработной плате продавцов
    public void getSalary() {
    }

    /// Данные о выработке определенного продавца
    public void getWorkBySeller() {
    }

    /// Данные о выработке на одного продавца
    public void getWorkForSeller() {
        windowName = Selections.sellers.getWindowName();
        showSelectWindow();
    }

    /// Секции
    public void getSectionsList() {
    }

    /// Торговые точки
    public void getPointsList() {
    }

    /// Продавцы
    public void getSellersList() {
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
