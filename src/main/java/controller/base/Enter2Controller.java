package controller.base;

import init.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import utils.Connection;
import utils.Roles;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Enter2Controller implements Initializable  {

    public final static String LOGIN_WINDOW_FXML = "/windows/enter2_window.fxml";

    private final Connection connection;

    public Enter2Controller() {
        connection = Main.getConnection();
    }

    @FXML
    private TextArea loginText;

    @FXML
    private PasswordField passwordText;

    @FXML
    public void loginButtonTapped(ActionEvent event) throws IOException {
        if (isNotEmpty()) {
            try {
                Roles role = connection.signIn(loginText.getText(), passwordText.getText());
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader();
                String windowName = role.getWindowName();
                Parent root = loader.load(getClass().getResourceAsStream(windowName));
                primaryStage.setScene(new Scene(root));
            } catch (SQLException ex) {
                System.out.println("SQLException: error with connection to server");
                showAlert("Invalid user data", "Check your login and password");
            } catch (ExceptionInInitializerError ex) {
                System.out.println("ExceptionInInitializerError: session is already exist");
                showAlert("session is already exist", "");
            }
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
