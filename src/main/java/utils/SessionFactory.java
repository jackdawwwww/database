//package utils;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.sql.*;
//import java.util.*;
//import java.util.function.Function;
//
//// Класс для работы с sql
//public class SessionFactory {
//
//    private static final String defaultIP = "84.237.50.81";
//    private static final String localIP = "127.0.0.1";
//    private static final String defaultPort = "1521";
//    private static final String login = "18209_khlimankova";
//    private static final String password = "khlimankova";
//    private static final String driver = "oracle.jdbc.driver.OracleDriver";
//    private static final String[] tableNames = {"Goods.sql"};//, "Providers.sql", "Sales.sql", "Seller.sql", "TradePoints.sql", "TradeTypes.sql"};
//
//    private static Connection connection;
//
//    public static void registerDefaultConnection() throws SQLException, ClassNotFoundException {
//        registerConnection(defaultIP, login, password);
//    }
//
//    public static void registerLocalhostConnection(String login, String password) throws SQLException, ClassNotFoundException {
//        registerConnection(localIP, login, password);
//    }
//
//    public static Connection getInstance() {
//        if (connection == null) {
//            throw new IllegalStateException();
//        }
//        return connection;
//    }
//
//    public static void createDatabase() {
//        createTables();
//    }
//
//    public static void close() throws SQLException {
//        if (connection != null) {
//            connection.close();
//            System.out.println("connection was closed");
//        } else {
//            System.out.println("connection is not registered");
//        }
//    }
//
//
//    public void executeQueryList(List<String> queryList) throws SQLException {
//        for (String list: queryList) {
//            try {
//                PreparedStatement statement = connection.prepareStatement(list);
//                statement.executeUpdate(list);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static ResultSet executeUpdateQuery(String sqlQuery) throws SQLException {
//        try {
//            PreparedStatement statement = connection.prepareStatement(sqlQuery);
//            statement.executeUpdate(sqlQuery);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static ResultSet executeQuery(String sql) {
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            return preparedStatement.executeQuery();
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//        return null;
//    }
//
//    public void insert(List<String> queryList) {
//        for(String query: queryList) {
//            try {
//                PreparedStatement statement = connection.prepareStatement(query);
//                statement.executeUpdate(query);
//                System.out.println("Insert done");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public List<List<String>> select(String sql) {
//        return select(sql, result -> {
//            try {
//                ArrayList<String> list = new ArrayList<>(1);
//                list.add(result.getString(1));
//                return list;
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            return null;
//        });
//    }
//
//
//    public List<List<String>> select(String sql, Function<ResultSet, List<String>> toString) {
//        List<List<String>> names = new LinkedList<>();
//        try {
//            Statement statement = connection.createStatement();
//            ResultSet result = statement.executeQuery(sql);
//            while (result.next()){
//                names.add(toString.apply(result));
//            }
//        }
//        catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return names;
//    }
//
//    public String createQuery(String template, String data) {
//        return template + data;
//    }
//
//    public void delete(String sql) {
//        try {
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.executeUpdate(sql);
//            System.out.println("Delete done");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void registerConnection(String ip, String login, String password) throws SQLException, ClassNotFoundException {
//        if (connection == null) {
//            Class.forName(driver);
//
//            Properties props = new Properties();
//            props.setProperty("user", login);
//            props.setProperty("password", password);
//
//            TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
//            TimeZone.setDefault(timeZone);
//            Locale.setDefault(Locale.ENGLISH);
//
//            System.out.println("register connection to " + ip + "...");
//            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":" + SessionFactory.defaultPort + ":", props);
//
//            if (connection.isValid(1)) {
//                System.out.println("success connection to " + ip);
//            } else {
//                System.out.println("bad connection to " + ip);
//            }
//
//        } else {
//            throw new IllegalStateException();
//        }
//    }
//
//    private static String writeScriptFromFile(String relativePath) {
//        try {
//            return new String(Files.readAllBytes(Paths.get(
//                    "src/main/resources/" + relativePath)));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    private static List<String> addSequences() {
//        List<String> sequences = new LinkedList<>();
//        for(String name: tableNames) {
//            sequences.add(writeScriptFromFile("sequences/" + name));
//        }
//        return sequences;
//    }
//
//    private static List<String> addAutoincrement() {
//        List<String> autoIncrements = new LinkedList<>();
//        for(String name: tableNames) {
//            autoIncrements.add(writeScriptFromFile("autoincrement/" + name));
//        }
//        return autoIncrements;
//    }
//
//    private List<String> deleteSequences() {
//        List<String> autoIncrements = new LinkedList<>();
//        for(String name: tableNames) {
//            autoIncrements.add(writeScriptFromFile("drop/dropSequences/" + name));
//        }
//        return autoIncrements;
//    }
//
//    private List<String> deleteTables() {
//        LinkedList<String> autoIncrements = new LinkedList<>();
//        for(String name: tableNames) {
//            autoIncrements.addFirst(writeScriptFromFile("drop/dropTables/" + name));
//        }
//        return autoIncrements;
//    }
//
//
//    private static void execute(List<String> queries) {
//        for (String query: queries) {
//            try {
//            //    Statement statement = connection.createStatement();
//                Statement statement = connection.prepareStatement(query);
//
//                statement.executeQuery(query);
//            } catch (java.sql.SQLIntegrityConstraintViolationException ignored) {
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static void createTables() {
//        List<String> createList = new LinkedList<>();
//
//        for(String name: tableNames) {
//            createList.add(writeScriptFromFile("tablesCreation/" + name));
//        }
//        try {
//            Statement statement = connection.createStatement();
//            statement.executeQuery(writeScriptFromFile("additional/createIfNotExists.sql"));
//        } catch (SQLException e) {
//            e.printStackTrace();
//
//        }
//
//        execute(createList);
//        execute(addSequences());
//        execute(addAutoincrement());
//    }
//}
//
