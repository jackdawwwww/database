package controller.roles;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain {

    public Button button;
    private String windowName;

    /// Оформить карту клиента
    public void newClient(ActionEvent actionEvent) {
    }

    /// Новая заявка
    public void newRequest(ActionEvent actionEvent) {
    }

    /// Мои заявки
    public void myRequests(ActionEvent actionEvent) {
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

//    /// Номенклатура и объем товаров в торговой точке
//    public void getNomenclature(ActionEvent actionEvent) {
//    }
//
//    /// Сведения об объеме и ценах на конкретный товар
//    public void getInfo(ActionEvent actionEvent) {
//    }
//
//
//    <Button layoutX="119.0" layoutY="246.0" mnemonicParsing="false" onAction="#getNomenclature" text="Номенклатура и объем товаров в торговой точке" />
//    <Button layoutX="114.0" layoutY="284.0" mnemonicParsing="false" onAction="#getInfo" text="Сведения об объеме и ценах на конкретный товар" />
