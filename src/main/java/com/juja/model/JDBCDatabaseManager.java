package com.juja.model;

import com.juja.model.config.ConfigDB;
import com.juja.model.config.ConfigMsg;
import com.juja.controller.util.UtilsCommand;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Scope(value = "prototype")
public class JDBCDatabaseManager implements DatabaseManager  {

    private static final String URL_CONNECT_DB = "jdbc:postgresql://localhost:5432/";

    private static final String SELECT_TABLE_NAMES = "SELECT table_name FROM information_schema.tables " +
            "WHERE table_schema='public' AND table_type='BASE TABLE'";
    private static final String SELECT_SIZE_TABLE = "SELECT COUNT(*) FROM ";
    private static final String DROP_TABLE = "DROP TABLE %s";
    private static final String SQL_INSERT = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String SQL_GET_TABLE_COLUMNS = "SELECT * FROM information_schema.columns " +
            "WHERE table_schema=? AND table_name = ?";
    private static final String CLEAR_TABLE = "TRUNCATE %s";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE %s (%s)";
    private static final String DELETE_DATA = "DELETE FROM %s WHERE (%s)";
    private static final String UPDATE_TABLE = "UPDATE %s SET %s WHERE %s= ?";

    private Connection connection;
    private String database;
    private String userName;


    public void defaultConnect() throws SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection(
                    ConfigDB.getProperty(ConfigDB.DB_URL),
                    ConfigDB.getProperty(ConfigDB.DB_LOGIN),
                    ConfigDB.getProperty(ConfigDB.DB_PASSWORD));
        }
    }

    @Override
    public void connect(String database, String userName, String password) {
        this.database = database;
        this.userName = userName;
        try {
            connection = DriverManager.getConnection(
                    URL_CONNECT_DB + database, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(
                    String.format(ConfigMsg.getProperty("db.err.format"),
                            database, userName), e);
        }
    }

    @Override
    public Connection getConnection() {
        checkConnection();
        return connection;
    }

    @Override
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception ignored) {}
        }
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        checkConnection();
        List<Map<String, Object>> result = new LinkedList<>();
        try (  Statement stmt = connection.createStatement();
               ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ) {
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> dataSet = UtilsCommand.getDataMap();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                result.add(dataSet);
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    @Override
    public int getSize(String tableName) {
        checkConnection();
        try (Statement  stmt = connection.createStatement();
             ResultSet rsCount = stmt.executeQuery(SELECT_SIZE_TABLE + tableName);
        ) {
            rsCount.next();
            return rsCount.getInt(1);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Set<String> getTableNames() {
        checkConnection();
        Set<String> tables = UtilsCommand.getDataSet();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_TABLE_NAMES);
        ) {
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
        } catch (Exception ignored) {
        }
        return tables;
    }

    @Override
    public void clear(String tableName) {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(CLEAR_TABLE, tableName));
        } catch (Exception ignored) {
        }
    }

    @Override
    public void drop(String tableName) {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DROP_TABLE, tableName));
        } catch (Exception ignored) {}
    }
    @Override
    public void delete(String tableName,  Map<String, Object> delValue) {
        checkConnection();
        String delete = getNameValuesFormated(delValue, "%s='%s'");
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(DELETE_DATA, tableName, delete));
        } catch (Exception ignored) {}
    }

    @Override
    public void create(String tableName, List<String> input) {
        checkConnection();
        String columnsName = input.stream()
                .collect(Collectors.joining(", "));
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(SQL_CREATE_TABLE, tableName, columnsName));
        } catch (Exception ignored) {}
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) {
        checkConnection();
        String tableNames = getNameFormated(input, "%s");
        String values = getValuesFormated(input, "'%s'");
        try ( Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format(SQL_INSERT, tableName, tableNames, values));
        } catch (Exception ignored) {}
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) {
        checkConnection();
        try {
            String tableNames = getNameFormated(newValue, "%s = ?");
            String nameId = getTableHead(tableName)
                    .stream()
                    .findFirst()
                    .get();
            String sql = String.format(UPDATE_TABLE, tableName, tableNames, nameId);
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                int index = 1;
                for (Object value : newValue.values()) {
                    ps.setObject(index, value);
                    index++;
                }
                ps.setObject(index, id);
                ps.executeUpdate();
            }
        } catch (Exception ignored) {}

    }

    @Override
    public List<String> getTableHead(String tableName) {
        checkConnection();
        try(PreparedStatement stmt = connection.prepareStatement(SQL_GET_TABLE_COLUMNS)) {
            stmt.setString(1, "public");
            stmt.setString(2, tableName);
            ResultSet rs = stmt.executeQuery();
            List<String> tables = UtilsCommand.getDataList();
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
    }

    @Override
    public String getDatabaseName() {
        return database;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    private String getNameFormated(Map<String, Object> input, String format) {

        return input.keySet()
                .stream()
                .map(entry -> String.format(format, entry))
                .collect(Collectors.joining(","));
    }

    private String getValuesFormated(Map<String, Object> input, String format) {

        return input.values()
                .stream()
                .map(entry -> String.format(format, entry))
                .collect(Collectors.joining(","));
    }

    private String getNameValuesFormated(Map<String, Object> delValue, String format) {
        return delValue.entrySet()
                .stream()
                .map(entry -> String.format(format, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));
    }

    private void checkConnection() {
        if (connection == null) {
            throw new RuntimeException(ConfigMsg.getProperty("db.noconnect"));
        }
    }
}
