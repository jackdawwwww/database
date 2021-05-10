package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Stream;

public class DatabaseManager {
    private static final String[] tableNamesArray = {"TradeTypes.sql", "Goods.sql", "TradePoints.sql", "TradeRoom.sql",
            "Seller.sql", "Purchase_compositions.sql", "Customers.sql", "Sales.sql", "Providers.sql", "Accounting.sql",
            "Deliveries.sql", "DeliveriesGoods.sql","TradeSectionPoint.sql", "Requests.sql", "Kioski.sql", "Lotki.sql",
            "Shops.sql", "Univermags.sql"};

    private final Connection connection;
    private final List<String> tablesName;

    public DatabaseManager(Connection connection) {
        this.connection = connection;
        tablesName = new LinkedList<>();
        tablesName.addAll(Arrays.asList(tableNamesArray));
    }

    public void createDatabase() { createTables(); }

    public void insertTradeType(String name) {
        List<String> tradeType = new LinkedList<>();
        tradeType.add("INSERT INTO Trade_types(name) VALUES('" + name + "')");
        connection.insert(tradeType);
        System.out.println("INSERT trade type");
    }

    public void insertTradeRoom(String point) {
        List<String> tradeType = new LinkedList<>();
        tradeType.add("INSERT INTO Trade_Room(trade_points_id) VALUES(" + point + ")");
        connection.insert(tradeType);
        System.out.println("INSERT trade room");
    }

    public void insertGood(String name) {
        List<String> good = new LinkedList<>();
        good.add("INSERT INTO Goods(name) VALUES('" + name + "')");
        connection.insert(good);
        System.out.println("INSERT good");
    }

    public void insertTradePoint(String name, String typeId, String point_size, String rent_price,
                                 String communal_payments, String number_of_counters) {
        List<String> tradePoint = new LinkedList<>();
        tradePoint.add("INSERT INTO Trade_Points(type, name, point_size, rent_price, " +
                "communal_payments, number_of_counters) VALUES(" + typeId + ", '" + name + "', "+ point_size + ", " +
                rent_price + ", " + communal_payments + ", " + number_of_counters + ")");
        connection.insert(tradePoint);
        System.out.println("INSERT trade point");
    }

    public static String getIdFrom(String item) {
        return getSubstring(" ID=", "ID=", item);
    }

    public static String getSubstring(String start1, String start2, String item) {
        String start = start1;
        int substringStartIndex = item.indexOf(start);
        if(substringStartIndex < 0) {
            start = start2;
            substringStartIndex = item.indexOf(start);
        }
        int endIndex = item.indexOf(',', substringStartIndex);
        if(endIndex < 0) {
            endIndex = item.indexOf('}', substringStartIndex);
        }

        return item.substring(substringStartIndex + start.length(), endIndex);
    }

    public void updateGood(String id, String name) {
        String sql = "update goods set name = '" + name + "' where id = " + id;
        List<String> good = new LinkedList<>();
        good.add(sql);
        connection.insert(good);
        System.out.println("UPDATE good");
    }

    public void updateTradeType(String id, String name) {
        String sql = "update Trade_types set name = '" + name + "' where id = " + id;
        List<String> type = new LinkedList<>();
        type.add(sql);
        connection.insert(type);
        System.out.println("UPDATE Trade_types");
    }

    public void updateTradeRoom(String id, String point) {
        String sql = "update Trade_Room set trade_points_id = " + point + " where id = " + id;
        List<String> room = new LinkedList<>();
        room.add(sql);
        connection.insert(room);
        System.out.println("UPDATE trade room");
    }

    public void updateTradePoint(String id, String typeId, String name, String point_size,
                                String rent_price, String communal_payments, String number_of_counters) {
        String sql = "update Trade_Points set type = " + typeId + ", name = '" + name + "', point_size = " +
                point_size + ", rent_price = " + rent_price + ", communal_payments = " + communal_payments +
                ", number_of_counters = " + number_of_counters + " where id = " + id;
        List<String> point = new LinkedList<>();
        point.add(sql);
        connection.insert(point);
        System.out.println("UPDATE trade point");
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
        BufferedReader reader = new BufferedReader(new InputStreamReader(DatabaseManager.class.getResourceAsStream("/sql/" + relativePath)));
        try {
            String str = reader.readLine();
            return str;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
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
            if(list != null) {
                execute(list);
            }
        }

        System.out.println("Insert default");
    }

    private List<String> getListOfScriptsFromFile(String relativePath) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(DatabaseManager.class.getResourceAsStream("/sql/" + relativePath)));
        List<String> queries = new LinkedList<>();
        Object[] lines = reader.lines().toArray();

        for (int i = 0; i < lines.length; i++) {
            queries.add(lines[i].toString());
        }
        return queries;


    }

}
