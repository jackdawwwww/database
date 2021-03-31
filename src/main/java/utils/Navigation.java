package utils;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Navigation {
    private final Scene scene;
    private Connection connection;

    private final List<Controller> controllers = new ArrayList<>();

    public Navigation(Stage stage) {
        connection = new Connection();
        scene = new Scene(new Pane());
        stage.setScene(scene);
    }

    public Controller load(String sUrl) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(sUrl));
            Node root = fxmlLoader.load();

            Controller controller = fxmlLoader.getController();
            controller.setView(root);

            return controller;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void show(Controller controller) {
        try {
            scene.setRoot((Parent) controller.getView());
            controllers.add(controller);

            System.out.println("Add to history: " + controller.toString() + ". Total scenes: " + controllers.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        if (controllers.size() > 1) {
            controllers.remove(controllers.get(controllers.size() - 1));
            scene.setRoot((Parent) controllers.get(controllers.size() - 1).getView());
        }

        System.out.println("GoBack: " + controllers.get(controllers.size() - 1).toString() + ". Total scenes: " + controllers.size());
    }


    public void clearHistory() {
        while (controllers.size() > 1) {
            controllers.remove(0);
        }

        System.out.println("ClearHistory. Total scenes: " + controllers.size());
    }

    public Connection getConnection() {
        return connection;
    }
}
