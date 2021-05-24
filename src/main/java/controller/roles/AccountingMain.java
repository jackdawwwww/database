package controller.roles;

import controller.base.TableController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Selections;

import java.io.IOException;
import java.sql.SQLException;

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
        Stage stage = new Stage();
        stage.setTitle("Accounting");

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream("/windows/table_window.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TableController tableController = loader.getController();
        String parameter = "ACCOUNTING";
        tableController.setParameter(parameter);
        try {
            tableController.loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void showSelectWindow() {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = loader.load(getClass().getResourceAsStream(windowName));
            primaryStage.setScene(new Scene(root));
        } catch (IOException ignored) {

        }
    }
}
