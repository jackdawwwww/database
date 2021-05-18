package controller.base;

import init.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import utils.Connection;
import utils.Roles;

import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {
    public static String windowName = "/windows/register_window.fxml";
    public Button loginButton;
    public TextArea loginText;
    public TextArea passwordText;
    public ChoiceBox<String> roleChoice;
    private Map<String, Roles> roles;
    ObservableList<String> list = FXCollections.<String>observableArrayList();

    private Connection connection;

    public void registerButtonTapped() {
        if(isNotEmpty()) {
            if (isNotEmpty()) {
                try {
                    String role = roles.get(roleChoice.getValue()).name();
                    connection.register(loginText.getText(), passwordText.getText(), role);
                } catch (SQLException ex) {
                    System.out.println("SQLException: error with connection to server");
                    showAlert("При ргистрации пользователя произошла ошибка", "Логин должен быть уникальным");
                } catch (ExceptionInInitializerError ex) {
                    System.out.println("ExceptionInInitializerError: session is already exist");
                    showAlert("session is already exist", "");
                }
            }
        }
    }


    private boolean isNotEmpty() {
        return loginText.getText().length() != 0
                && passwordText.getText().length() != 0
                && !roleChoice.getValue().isEmpty();
    }

    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection = Main.getConnection();
        roles = Roles.getRoles();
        list.addAll(roles.keySet());
        roleChoice.setItems(list);
    }
}
