package com.juja.model;

import com.juja.controller.util.UtilsCommand;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JDBCDatabasaeManager2 implements DatabaseManager {

    private Connection connection;
    private JdbcTemplate template;

    @Override
    public void connect(String database, String userName, String password) {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database,
                    userName, password
            );
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        } catch (SQLException e) {
            connection = null;
            template = null;
        }
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public int getSize(String tableName) {
        return template.queryForObject("SELECT COUNT(*) FROM public." +
                tableName, Integer.class);
    }

    @Override
    public Set<String> getTableNames() {
        return null;
    }

    @Override
    public List<String> getTableHead(String tableName) {
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";
        return template.query(sql, (resultSet, rowNum) ->
                resultSet.getString("table_name"));
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        return template.query("SELECT * FROM public." + tableName,
                (rs, rowNum) -> {
                    Map<String, Object> dataSet = UtilsCommand.getDataMap();
                    ResultSetMetaData rsmd = rs.getMetaData();
                    for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                        dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                    }
                    return dataSet;
                });
    }

    @Override
    public void clear(String tableName) {
        template.execute("DELETE FROM public." + tableName);
    }

    @Override
    public void drop(String tableName) {

    }

    @Override
    public void delete(String tableName, Map<String, Object> delValue) {

    }

    @Override
    public void create(String tableName, List<String> input) {


    }

    @Override
    public void insert(String tableName, Map<String, Object> input) {
        String tableNames = getNameValuesFormatted(input, "%s");
        //String tableNames2 = StringUtils.collectionToCommaDelimitedString(input.keySet()., ",")
        String values = getNameValuesFormatted(input, "'%s'");
        String sql = String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, tableNames, values);
        template.update(sql);
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) {
        String tableNames = getNameValuesFormatted(newValue, "%s");
        String nameId = getTableHead(tableName)
                .stream()
                .findFirst()
                .get();
        List<Object> list = new LinkedList<>(newValue.values());
        String sql = String.format("UPDATE %s SET %s WHERE %s= ?", tableName, tableNames, nameId);
        list.add(id);
        template.update(sql, list.toArray());
    }

    @Override
    public void disconnect() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    private String getValuesFormated(Map<String, Object> input, String format) {
        return input.values()
                .stream()
                .map(entry -> String.format(format, entry))
                .collect(Collectors.joining(","));
    }

    private String getNameValuesFormatted(Map<String, Object> delValue, String format) {
        return delValue.entrySet()
                .stream()
                .map(entry -> String.format(format, entry.getKey(), entry.getValue()))
                .collect(Collectors.joining(", "));
    }
}
