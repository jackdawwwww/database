package controller.roles;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Selections;

import java.io.IOException;

public class AccountingMain {
    public Button button;
    private String windowName;

    /// Номенклатура и объем товаров в указанной торговой точке
    public void getNomenclature() {
        windowName = Selections.nomenclature.getWindowName();
        showSelectWindow();
    }

    /// Данные об объеме продаж указанного товара за некоторый период
    public void getSalesData() {

    }

    /// Данные о рентабельности торговой точки
    public void getRentable() {
    }

    /// Данные о товарообороте
    public void getData() {
    }

    /// Просмотр таблицы
    public void accounting() {
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
