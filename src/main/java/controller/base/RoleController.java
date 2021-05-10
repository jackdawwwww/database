package controller.base;

import javafx.scene.control.Button;
import utils.Roles;

public class RoleController {

    static String FXML = "/windows/role_window.fxml";
    private Roles role = Roles.admin;
    public Button AdminButton;

    public void selTapped() {
        role = Roles.seller;
        showNextWindow();
    }

    public void cliTapped() {
        role = Roles.client;
        showNextWindow();
    }

    public void manTapped() {
        role = Roles.manager;
        showNextWindow();
    }

    public void accTapped() {
        role = Roles.accounting;
        showNextWindow();
    }

    public void adminTapped() {
        role = Roles.admin;
        showNextWindow();
    }

    public void selManTapped() {
        role = Roles.sellerManager;
        showNextWindow();
    }

    private void showNextWindow() {
        String windowName = role.getWindowName();

    }
}
