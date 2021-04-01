package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

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

    public void insertTradeType(String name) {
        List<String> tradeType = new LinkedList<>();
        tradeType.add("INSERT INTO TradeTypes(name) VALUES('" + name + ")");
        connection.insert(tradeType);
        System.out.println("INSERT trade type");
    }

    public void insertGood(String name) {
        List<String> good = new LinkedList<>();
        good.add("INSERT INTO Goods(name) VALUES('" + name + ")");
        connection.insert(good);
        System.out.println("INSERT good");
    }

    public void insertTradePoint(int typeId, String name, int point_size, int rent_price, int communal_payments, int number_of_counters) {
        List<String> tradePoint = new LinkedList<>();
        tradePoint.add("INSERT INTO TradePoints(type, name, point_size, rent_price, " +
                "communal_payments, number_of_counters) VALUES('" + typeId + "', " + name + ", "+ point_size + ", " +
                rent_price + ", " + communal_payments + ", " + number_of_counters + ")");
        connection.insert(tradePoint);
        System.out.println("INSERT trade point");
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
        insertDefault();
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

    private void insertDefault() {
        for(String tableName: tablesName) {
            List<String> list = getListOfScriptsFromFile("insertions/" + tableName);
            if(!list.isEmpty()) {
                execute(list);
            }
        }

        System.out.println("Insert default");
    }

    private List<String> getListOfScriptsFromFile(String relativePath) {
        try {
            Scanner scanner = new Scanner(new String(Files.readAllBytes(Paths.get(
                    "src/main/resources/" + relativePath))));
            List<String> queries = new LinkedList<>();
            while (scanner.hasNext()) {
                String script = scanner.nextLine();
                queries.add(script);
            }
            return queries;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
