package init;

import controller.EntranceController;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.Connection;
import utils.Navigation;

public class Main extends Application {
    private static Navigation navigation;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Information system of trade organization");

        navigation = new Navigation(stage);
        Main.getNavigation().load(EntranceController.ENTRANCE_WINDOW_FXML).show();

        stage.show();
    }

    @Override
    public void stop() throws Exception {
        Main.getNavigation().getConnection().close();
        super.stop();
    }

    public static Navigation getNavigation() {
        return navigation;
    }

    public static Connection getConnection() { return navigation.getConnection(); }

}
