package com.juja.sqlcmd_ee.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.*;

@Component
public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;
    private JdbcTemplate template;
    private String database;
    private String userName;

    @Override
    public List<DataSet> getTableData(String tableName) {
        return template.query("SELECT * FROM public." + tableName,
                new RowMapper<DataSet>() {
                    public DataSet mapRow(ResultSet rs, int rowNum) throws SQLException {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        DataSet dataSet = new DataSetImpl();
                        for (int i = 0; i < rsmd.getColumnCount(); i++) {
                            dataSet.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
                        }
                        return dataSet;
                    }
                }
        );
    }

    @Override
    public int getSize(String tableName) {
        return template.queryForObject("SELECT COUNT(*) FROM public." + tableName, Integer.class);
    }

    @Override
    public Set<String> getTableNames() {
        return new LinkedHashSet<>(template.query(
                "SELECT table_name FROM information_schema.tables " +
                        "WHERE table_schema='public' AND table_type='BASE TABLE'",
                new RowMapper<String>() {
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString("table_name");
                    }
                }
        ));
    }

    @Override
    public void connect(String database, String userName, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Please add jdbc jar to project.", e);
        }
        try {
            if (connection != null) {
                connection.close();
            }
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/" + database, userName, password);
            this.database = database;
            this.userName = userName;
            template = new JdbcTemplate(new SingleConnectionDataSource(connection, false));
        } catch (SQLException e) {
            connection = null;
            template = null;
            throw new RuntimeException(
                    String.format("Cant get connection for model:%s user:%s", database, userName), e);
        }
    }

    @Override
    public void clear(String tableName) {
        template.execute("DELETE FROM public." + tableName);
    }

    @Override
    public void create(String tableName, DataSet input) {
        String tableNames = StringUtils.collectionToDelimitedString(input.getNames(), ",");
        String values = StringUtils.collectionToDelimitedString(input.getValues(), ",", "'", "'");

        template.update(String.format("INSERT INTO public.%s (%s) VALUES (%s)",
                tableName, tableNames, values));
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        String tableNames = StringUtils.collectionToDelimitedString(newValue.getNames(), ",", "", " = ?");

        String sql = "UPDATE public." + tableName + " SET " + tableNames + " WHERE id = ?";

        List<Object> objects = new LinkedList<>(newValue.getValues());
        objects.add(id);

        template.update(sql, objects.toArray());
    }

    @Override
    public Set<String> getTableColumns(String tableName) {
        return new LinkedHashSet<>(template.query(
                "SELECT * FROM information_schema.columns " +
                        "WHERE table_schema = 'public' AND table_name = '" + tableName + "'",
                (rs, rowNum) -> rs.getString("column_name")
        ));
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
}