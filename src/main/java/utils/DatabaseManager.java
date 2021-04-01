package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DatabaseManager {
    private static final String[] tableNamesArray = {"TradeTypes.sql", "Goods.sql", "TradePoints.sql"};
//            "TradeRoom.sql", "Seller.sql", "Purchase_compositions.sql", "Customers.sql", "Sales.sql",
//            "Providers.sql", "Accounting.sql","Deliveries.sql", "DeliveriesGoods.sql","TradeSectionPoint.sql"};

    private final Connection connection;
    private final List<String> tablesName;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
        tablesName = new LinkedList<>();
        tablesName.addAll(Arrays.asList(tableNamesArray));
    }

    public void createDatabase() {
        createTables();
    }

    private void execute(List<String> queries) {
        for (String query: queries) {
            try {
                connection.executeQuery(query);
            } catch (SQLIntegrityConstraintViolationException ignored) {
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createTables() {
        List<String> tablesCreation = new LinkedList<>();
        for(String tableName: tablesName) {
            tablesCreation.add(getScriptFromFile("tablesCreation/" + tableName));
        }

        execute(getTableDrops());
        execute(getSequencesDrops());
        execute(tablesCreation);
        execute(getSequences());
        execute(getAutoincrement());
    }

    private String getScriptFromFile(String relativePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(
                    "src/main/resources/" + relativePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> getSequences() {
        List<String> sequences = new LinkedList<>();
        for(String name: tablesName) {
            sequences.add(getScriptFromFile("sequences/" + name));
        }
        return sequences;
    }

    private List<String> getAutoincrement() {
        List<String> autoIncrements = new LinkedList<>();
        for(String name: tablesName) {
            autoIncrements.add(getScriptFromFile("triggers/" + name));
        }
        return autoIncrements;
    }

    private List<String> getSequencesDrops() {
        List<String> autoIncrements = new LinkedList<>();
        for(String tableName: tablesName) {
            autoIncrements.add(getScriptFromFile("dropSequences/" + tableName));
        }
        return autoIncrements;
    }

    private List<String> getTableDrops() {
        LinkedList<String> autoIncrements = new LinkedList<>();
        for(String tableName: tablesName) {
            autoIncrements.addFirst(getScriptFromFile("dropTables/" + tableName));
        }
        return autoIncrements;
    }
}
