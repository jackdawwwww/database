package controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Roles;

import java.io.IOException;

public class RoleController {

    static String FXML = "/windows/role_window.fxml";
    private Roles role = Roles.admin;
    private ActionEvent actionEvent;

    public void selTapped(ActionEvent actionEvent) {
        role = Roles.seller;
        this.actionEvent = actionEvent;
        showNextWindow();
    }

    public void cliTapped(ActionEvent actionEvent) {
        role = Roles.client;
        this.actionEvent = actionEvent;
        showNextWindow();
    }

    public void manTapped(ActionEvent actionEvent) {
        role = Roles.reqManager;
        this.actionEvent = actionEvent;
        showNextWindow();
    }

    public void accTapped(ActionEvent actionEvent) {
        role = Roles.accounting;
        this.actionEvent = actionEvent;
        showNextWindow();
    }

    public void adminTapped(ActionEvent actionEvent) {
        role = Roles.admin;
        this.actionEvent = actionEvent;
        showNextWindow();
    }

    public void selManTapped(ActionEvent actionEvent) {
        role = Roles.sellerManager;
        this.actionEvent = actionEvent;
        showNextWindow();
    }

    public void provTapped(ActionEvent actionEvent) {
        role = Roles.provider;
        this.actionEvent = actionEvent;
        showNextWindow();
    }

    private void showNextWindow() {
        String resource = role.getWindowName();
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();

        try {
            Parent root = loader.load(getClass().getResourceAsStream(resource));
            primaryStage.setScene(new Scene(root));
        } catch (IOException ignored) {

        }
    }
}
