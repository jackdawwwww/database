package controller.roles;

import controller.base.TableController;
import controller.insert.InsertMode;
import controller.insert.RequestInsertController;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Selections;

import java.io.IOException;
import java.sql.SQLException;

public class ManagerMain {

    public Button button;
    private String windowName;

    /// Оформить заказ поставщику
    public void newReq() {
        String windowName = "/windows/insertWindows/request_insert_window.fxml";
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream(windowName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestInsertController controller = loader.getController();
        controller.setCanChooseProvider(true);
        controller.setCanChooseStatus(false);

        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    /// Просмотреть заявки
    public void getReq() {
        Stage stage = new Stage();
        stage.setTitle("Заявки");

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream("/windows/table_window.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TableController tableController = loader.getController();
        String parameter = "REQUESTS";
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

    /// Номенклатура товаров
    public void getNom() {
        windowName = Selections.nomenclature.getWindowName();
        showSelectWindow();
    }

    /// Поставщики, поставляющие указанный вид товара
    public void getProvByParam() {
        windowName = Selections.suppliers.getWindowName();
        showSelectWindow();
    }

    /// Получить данные о выработке на одного продавца за указанный период по всем торговым точкам, по торговым точкам заданного типа.",
    public void getSellersDataByParam() {
        windowName = Selections.sellers.getWindowName();
        showSelectWindow();
    }

    /// Получить сведения о покупателях указанного товара за обозначенный, либо за весь период, по всем торговым точкам, по торговым точкам указанного типа, по данной торговой точке.
    public void getClientsDataByParam() {
        windowName = Selections.clients.getWindowName();
        showSelectWindow();
    }


    private void showSelectWindow() {
        Stage primaryStage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = loader.load(getClass().getResourceAsStream(windowName));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException ignored) {

        }

        primaryStage.show();
    }
}
