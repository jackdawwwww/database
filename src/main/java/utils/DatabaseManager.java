package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class DatabaseManager {
    private static final String[] tableNamesArray = {"TradeTypes.sql", "Goods.sql", "TradePoints.sql"}; //, "Seller.sql", "Sales.sql",
           // "Providers.sql", "Accounting.sql", "Customers.sql", "Deliveries.sql", "Deliveries_goods", "Purchase_compositions.sql",
           // "TradeRoom.sql", "TradeSectionPoints.sql"};

    private Connection connection;
    private List<String> tablesName;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
        tablesName = new LinkedList<>();
        tablesName.addAll(Arrays.asList(tableNamesArray));
    }

    public void createDatabase() throws SQLException {
        createTables();
    }

    private void execute(List<String> queries, Optional<Integer> additionalCode) throws SQLException {
        for (String query: queries) {
            try {
                connection.executeQuery(query);
            } catch (SQLIntegrityConstraintViolationException ignored) {
            } catch (SQLException e) {
                if (!(e.getErrorCode() == 6550 || e.getErrorCode() == additionalCode.get())) {
                    e.printStackTrace();
                }
            } finally {
                System.out.println("Execute query: " + query.toString());
            }
        }
    }

    private void createTables() throws SQLException {
        List<String> tablesCreation = new LinkedList<>();
        for(String tableName: tablesName) {
            tablesCreation.add(getScriptFromFile("tablesCreation/" + tableName));
        }

        execute(getTableDrops(), null);
        execute(getSequencesDrops(), null);
        execute(tablesCreation, Optional.of(955));
        execute(getSequences(), null);
        execute(getAutoincrement(), null);
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
