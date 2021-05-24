package controller.roles;

import controller.additional.ClientReqTableController;
import controller.additional.ProvReqTableController;
import controller.insert.RequestInsertController;
import controller.select.SelectController;
import init.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ProviderMain implements SelectController {

    public Button button;
    private String windowName = "";

    // Просмотр активных заявок
    public void getActive() {
        String sql = "WITH t1 AS (SELECT * FROM requests where provider IS NOT NULL), t2 AS (SELECT * from providers where user_id IS NOT NULL) SELECT t1.id, provider, trade_point, good, count, status from t1 LEFT JOIN t2 ON t1.provider = t2.id where t1.status = 0 and t2.user_id = " + Main.getConnection().userId + "order by t1.id";
        Stage stage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        Parent root = null;

        try {
            root = loader.load(getClass().getResourceAsStream("/windows/additional/prov_req_table_window.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProvReqTableController controller = loader.getController();
        controller.setSQL(sql);

        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    // История заявок
    public void getStory() {
        String sql = "WITH t1 AS (SELECT * FROM requests where provider IS NOT NULL), t2 AS (SELECT * from providers where user_id IS NOT NULL) SELECT trade_point, good as good_id, count, status from t1 LEFT JOIN t2 ON t1.provider = t2.id where t2.user_id = " + Main.getConnection().userId;
        showResult(sql);
    }

    // Наши товары
    public void getGoods() {
        String sql = "WITH t1 AS (SELECT * from Providers INNER JOIN Deliveries_goods ON providers.id = Deliveries_goods.provider_id where Providers.user_id IS NOT NULL) " +
                "SELECT goods.id as good_id, goods.name as good_name, price from goods INNER JOIN t1 on t1.good_id = goods.id where t1.user_id = " + Main.getConnection().userId;
        showResult(sql);
    }

    // Наши поставки
    public void getDel() {
        String sql = "WITH t1 AS (SELECT * FROM deliveries), t2 AS (SELECT * from providers where user_id IS NOT NULL) SELECT good_id, count, del_date from t1 LEFT JOIN t2 ON t1.provider_id = t2.id where t2.user_id = " + Main.getConnection().userId;
        showResult(sql);
    }
}
