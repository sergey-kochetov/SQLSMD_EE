package com.juja.sqlcmd_ee.dao;

import com.juja.sqlcmd_ee.model.DataSet;

import java.util.List;
import java.util.Set;

public interface DatabaseManager {

    void connect(String database, String userName, String password);

    void disconnect();

    Set<String> getTables();

    List<String> getTableHead(String tableName);

    List<DataSet> getTableData(String tableName);

    void createTable(String tableName);
//
//    void insert(String tableName, DataSet input);
//
//    void update(String tableName, int id, DataSet newValue);
//
//    void delete(String tableName, int id);

    void addColumn(String tableName, String columnName, String dataType);

    void dropColumn(String tableName, String columnName);
}
