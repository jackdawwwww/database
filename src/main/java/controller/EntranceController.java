package controller;

import init.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import utils.Connection;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EntranceController extends BaseController implements Initializable {
    public final static String ENTRANCE_WINDOW_FXML = "/entrance_window.fxml";
    public final static String LOGIN_WINDOW_FXML = "/login_window.fxml";

    private Connection connection;

    public EntranceController() {
        connection = Main.getConnection();
    }

    @FXML
    private TextArea loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Button defaultButton;

    @FXML
    private Button localhostButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;


    @FXML
    public void defaultButtonTapped() {
        try {
            connection.registerDefaultConnection();
            Main.getNavigation().load(MainController.URL_FXML).show();
        } catch (SQLException ex) {
            System.out.println("SQLException: error with connection to server");
            showAlert("error with connection to server", "");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException: error with driver manager");
            showAlert("error with driver manager", "");
        } catch (ExceptionInInitializerError ex) {
            System.out.println("ExceptionInInitializerError: session is already exist");
            showAlert("session is already exist", "");
        }
    }

    @FXML
    public void localhostButtonTapped() {
        Main.getNavigation().load(LOGIN_WINDOW_FXML).show();
    }

    @FXML
    public void backButtonTapped() {
        Main.getNavigation().goBack();
    }

    @FXML
    public void loginButtonTapped() {
        if (isNotEmpty()) {
            try {
                connection.registerLocalhostConnection(loginText.getText(), passwordText.getText());
                Main.getNavigation().load(MainController.URL_FXML).show();
            } catch (SQLException ex) {
                System.out.println("SQLException: error with connection to server");
                showAlert("error with connection to server", "Check your login and password");
            } catch (ClassNotFoundException ex) {
                System.out.println("ClassNotFoundException: error with driver manager");
                showAlert("error with driver manager", "");
            } catch (ExceptionInInitializerError ex) {
                System.out.println("ExceptionInInitializerError: session is already exist");
                showAlert("session is already exist", "");
            }
        } else {
            showAlert("Empty field!", "Enter login and password");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean isNotEmpty() {
        return loginText.getText().length() != 0
                && passwordText.getText().length() != 0;
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}