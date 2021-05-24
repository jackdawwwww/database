package controller.roles;

import controller.additional.ClientReqTableController;
import controller.base.TableController;
import controller.insert.InsertMode;
import controller.insert.RequestInsertController;
import init.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ClientMain {

    public Button myReq;
    public Button newReq;

    /// Новая заявка
    public void newRequest() {
        Stage stage = new Stage();
        stage.setTitle("Новая заявка");

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream("/windows/insertWindows/request_insert_window.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        RequestInsertController tableController = loader.getController();
        tableController.setCanChooseProvider(false);

        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    /// Мои заявки
    public void myRequests(ActionEvent actionEvent) {
        String sql = "select * from requests where user_id = " + Main.getConnection().userId;
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream("/windows/additional/client_req_table_window.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientReqTableController controller = loader.getController();
        controller.setSQL(sql);

        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }
}