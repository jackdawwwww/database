package controller.base;

import init.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.Connection;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EntranceController implements Initializable {
    public final static String ENTRANCE_WINDOW_FXML = "/windows/entrance_window.fxml";
    public final static String LOGIN_WINDOW_FXML = "/windows/login_window.fxml";

    private final Connection connection;

    public EntranceController() {
        connection = Main.getConnection();
    }

    @FXML
    private TextArea loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    public void defaultButtonTapped(ActionEvent event) throws IOException {
        try {
            connection.registerDefaultConnection();
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResourceAsStream(RoleController.FXML));

            primaryStage.setScene(new Scene(root));
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
    public void localhostButtonTapped(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(LOGIN_WINDOW_FXML));

        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void backButtonTapped(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(ENTRANCE_WINDOW_FXML));

        primaryStage.setScene(new Scene(root));
    }

    @FXML
    public void loginButtonTapped(ActionEvent event) throws IOException {
        if (isNotEmpty()) {
            try {
                connection.registerLocalhostConnection(loginText.getText(), passwordText.getText());
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResourceAsStream(MainTableController.FXML));

                primaryStage.setScene(new Scene(root));
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