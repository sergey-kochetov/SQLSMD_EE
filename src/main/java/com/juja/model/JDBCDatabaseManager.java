package com.juja.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class JDBCDatabaseManager implements DatabaseManager  {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Connection connection;

    @Override
    public void connect(String database, String userName, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName, password);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Set<Map<String, Object>> getTableData(String tableName) throws SQLException {
        checkConnection();
        try (  Statement stmt = connection.createStatement();
               ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
        ) {
            ResultSetMetaData rsmd = rs.getMetaData();
            Set<Map<String, Object>> result = new LinkedHashSet<>();
            while (rs.next()) {
                Map<String, Object> dataSet = new LinkedHashMap<>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                result.add(dataSet);
            }
            return result;
        }
    }

    @Override
    public int getSize(String tableName) throws SQLException {
        checkConnection();
        try (Statement  stmt = connection.createStatement();
             ResultSet rsCount = stmt.executeQuery(String.format("SELECT COUNT(*) FROM %s", tableName));
        ) {
            rsCount.next();
            return rsCount.getInt(1);
        }
    }

    @Override
    public Set<String> getTableHead() throws SQLException {
        checkConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema='public' AND table_type='BASE TABLE'");
        ) {
            Set<String> tables = new LinkedHashSet<>();
            while (rs.next()) {
                tables.add(rs.getString("table_name"));
            }
            return tables;
        }
    }

    @Override
    public void truncate(String tableName) throws SQLException {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format("TRUNCATE %s", tableName));
        }
    }

    @Override
    public void drop(String tableName) throws SQLException {
        checkConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format("DROP TABLE %s", tableName));
        }
    }
    @Override
    public void delete(String tableName,  Map<String, Object> delValue) throws SQLException {
        checkConnection();
        String delete = getNameValuesFormated(delValue, "%s='%s'");
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format("DELETE FROM %s WHERE (%s)", tableName, delete));
        }
    }

    @Override
    public void create(String tableName, List<String> input) throws SQLException {
        checkConnection();
        String columnsName = String.join(", ", input);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format("CREATE TABLE %s (%s)", tableName, columnsName));
        }
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) throws SQLException {
        checkConnection();
        String tableNames = getNameFormated(input, "%s");
        String values = getValuesFormated(input, "'%s'");
        try ( Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, tableNames, values));
        }
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) throws SQLException {
        checkConnection();
        String tableNames = getNameFormated(newValue, "%s = ?");
        String nameId = getTableColumns(tableName)
                .stream()
                .findFirst()
                .get();
        String sql = String.format("UPDATE %s SET %s WHERE %s= ?", tableName, tableNames, nameId);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int index = 1;
            for (Object value : newValue.values()) {
                ps.setObject(index, value);
                index++;
            }
            ps.setObject(index, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Set<String> getTableColumns(String tableName) throws SQLException {
        checkConnection();
        try(PreparedStatement stmt = connection.prepareStatement("SELECT * FROM information_schema.columns " +
                "WHERE table_schema=? AND table_name = ?")) {
            stmt.setString(1, "public");
            stmt.setString(2, tableName);
            ResultSet rs = stmt.executeQuery();
            Set<String> tables = new LinkedHashSet<>();
            while (rs.next()) {
                tables.add(rs.getString("column_name"));
            }
            return tables;
        } catch (SQLException e) {
            return Collections.emptySet();
        }
    }

    @Override
    public boolean isConnected() {
        return connection != null;
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
            throw new RuntimeException("No connection");
        }
    }
}
