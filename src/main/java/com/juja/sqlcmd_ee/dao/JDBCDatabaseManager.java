package com.juja.sqlcmd_ee.dao;

import com.juja.sqlcmd_ee.model.DataSet;
import com.juja.sqlcmd_ee.model.DataSetImpl;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
@Scope(value = "prototype")
@Data
public class JDBCDatabaseManager implements DatabaseManager {

    private Connection connection;
    private JdbcTemplate template;
    private String database;
    private String userName;

    @Override
    public void connect(String database, String userName, String password) {
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
    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(
                    String.format("Cant diconnection for model:%s user:%s", database, userName), e);
        } finally {
            connection = null;
        }
    }

    @Override
    public Set<String> getTables() {
        String sql = "SELECT table_name FROM information_schema.tables " +
                "WHERE table_schema='public' AND table_type='BASE TABLE'";
        return new LinkedHashSet<>(
                template.query(sql, (rs, rowNum) -> rs.getString("table_name")
                ));
    }

    @Override
    public List<String> getTableHead(String tableName) {
        String sql = String.format("SELECT * FROM information_schema.columns " +
                "WHERE table_schema = 'public' AND table_name = '%s'", tableName);
        return new LinkedList<>(template.query(sql,
                (rs, rowNum) -> rs.getString("column_name")
        ));
    }

    @Override
    public List<DataSet> getTableData(String tableName) {
        String sql = String.format("SELECT * FROM public.%s", tableName);
        return template.query(sql, (rs, rowNum) -> {
                    ResultSetMetaData rsmd = rs.getMetaData();
                    DataSet dataSet = new DataSetImpl();
                    for (int i = 0; i < rsmd.getColumnCount(); i++) {
                        dataSet.put(rsmd.getColumnName(i + 1), rs.getObject(i + 1));
                    }
                    return dataSet;
                }
        );
    }

//    @Override
//    public void createTable(String tableName) {
//        String sql = String.format("CREATE TABLE %s(id SERIAL PRIMARY KEY)", tableName);
//        template.update(sql);
//    }
//
//    @Override
//    public void addColumn(String tableName, String columnName, String dataType) {
//        String sql = String.format("ALTER TABLE %s ADD COLUMN %s %s;", tableName, columnName, dataType);
//        template.update(sql);
//    }
//
//    @Override
//    public void dropColumn(String tableName, String columnName) {
//        String sql = String.format("ALTER TABLE %s DROP COLUMN IF EXISTS %s", tableName, columnName);
//        template.update(sql);
//    }
//
//    @Override
//    public void insert(String tableName, DataSet input) {
//        String tableNames = StringUtils.collectionToDelimitedString(input.getNames(), ",");
//        String values = StringUtils.collectionToDelimitedString(input.getValues(), ",", "'", "'");
//
//        template.update(String.format("INSERT INTO public.%s (%s) VALUES (%s)",
//                tableName, tableNames, values));
//    }
//
//    @Override
//    public void update(String tableName, int id, DataSet newValue) {
//        String tableNames = StringUtils.collectionToDelimitedString(newValue.getNames(), ",", "", " = ?");
//
//        String sql = String.format("UPDATE public.%s SET %s WHERE id = ?", tableName, tableNames);
//
//        List<Object> list = new LinkedList<>(newValue.getValues());
//        list.add(id);
//        template.update(sql, list.toArray());
//    }
//
//    @Override
//    public void delete(String tableName, int id) {
//        template.update(String.format("DELETE FROM %s WHERE id=%d", tableName, id));
//    }
}
