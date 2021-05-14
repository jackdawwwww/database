package controller.roles;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Selections;

import java.io.IOException;

public class ManagerMain {

    public Button button;
    private String windowName;

    /// Оформить заказ поставщику
    public void newReq() {
    }

    /// Просмотреть заявки
    public void getReq() {
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

    /// Покупатели, купившие указанный вид товара
    public void getSelByParam() {
    }

    /// Данные об объеме продаж указанного товара
    public void getDataByGood() {
    }

    /// Сведения о поставках определенного товара указанным поставщиком
    public void getDataByProv() {
    }

    /// Сведения о поставках товаров по указанному номеру заказа
    public void getDataByReq() {
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
