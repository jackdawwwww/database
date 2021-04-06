package init;

import controller.base.EntranceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Connection;
import java.io.IOException;

public class Main extends Application {
    private static final Connection connection = new Connection();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Information system of trade organization");
        Parent root = new FXMLLoader().load(getClass().getResourceAsStream(EntranceController.ENTRANCE_WINDOW_FXML));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        connection.close();
        super.stop();
    }

    public static Connection getConnection() { return connection; }

}

