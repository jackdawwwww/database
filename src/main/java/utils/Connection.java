package utils;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class Connection {

    private java.sql.Connection connection;

    private static final String defaultIP = "84.237.50.81";
    private static final String localIP = "127.0.0.1";
    private static final String defaultPort = "1521";
    private static final String login = "18209_khlimankova";
    private static final String password = "khlimankova";
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    public String userId;

    public Connection() { }

    public void registerDefaultConnection() throws SQLException, ClassNotFoundException {
        registerConnection(defaultIP, login, password);
    }

    public void registerLocalhostConnection(String login, String password) throws SQLException, ClassNotFoundException {
        registerConnection(localIP, login, password);
    }

    private void registerConnection(String ip, String login, String password) throws SQLException, ClassNotFoundException {
        Class.forName(driver);

        Properties props = new Properties();
        props.setProperty("user", login);
        props.setProperty("password", password);
        createConnection();

        System.out.println("register connection to " + ip + "...");
        connection = DriverManager.getConnection("jdbc:oracle:thin:@" + ip + ":" + Connection.defaultPort + ":", props);

        if (connection.isValid(1)) {
            System.out.println("success connection to " + ip);
        } else {
            System.out.println("bad connection to " + ip);
        }
    }

    public Roles signIn(String login, String password) throws SQLException {
        Roles role = null;
        ResultSet set = executeQueryAndGetResult("SELECT role from users where (username = '" + login
                + "') and (password = '" + password + "')");

        ResultSet set2 = executeQueryAndGetResult("SELECT id from users where (username = '" + login
                + "') and (password = '" + password + "')");

        if (set != null) {
            while (set.next()) {
                String name = set.getString(1);
                role = Roles.getRoleByName(name);
            }
        }

        if (set2 != null) {
            while (set2.next()) {
                String id = set2.getString(1);
                userId = id;
            }
        }

        return role;
    }

    public void register(String login, String password, String roleName) throws SQLException {
        String sql = "INSERT INTO users (username, password, role) VALUES ('" + login + "', '" + password + "','" + roleName + "')";
        executeQuery(sql);
    }

    private void createConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        TimeZone timeZone = TimeZone.getTimeZone("GMT+7");
        TimeZone.setDefault(timeZone);
        Locale.setDefault(Locale.ENGLISH);
    }

    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
            System.out.println("connection was closed");
        } else {
            System.out.println("connection is not registered");
        }
    }

    public ResultSet executeQueryAndGetResult(String sql) {
        createConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public void executeQuery(String sql) throws SQLException {
        createConnection();
        try(PreparedStatement preStatement = connection.prepareStatement(sql)) {
            preStatement.executeUpdate(sql);
        }
    }

    public void insert(List<String> queryList) {
        createConnection();
        for(String query : queryList) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<List<String>> select(String sql) {
        return select(sql, result -> {
            try {
                ArrayList<String> list = new ArrayList<>(1);
                list.add(result.getString(1));
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        });
    }


    public List<List<String>> select(String sql, Function<ResultSet, List<String>> toString){
        createConnection();
        List<List<String>> names = new LinkedList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            while (result.next()){
                names.add(toString.apply(result));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return names;
    }

    public void delete(String query) {
        createConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}