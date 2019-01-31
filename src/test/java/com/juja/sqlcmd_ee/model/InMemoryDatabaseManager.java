package com.juja.sqlcmd_ee.model;

import com.juja.sqlcmd_ee.dao.DatabaseManager;

import java.util.*;

public class InMemoryDatabaseManager implements DatabaseManager {

    private Map<String, List<DataSet>> tables = new LinkedHashMap<>();

    @Override
    public List<DataSet> getTableData(String tableName) {
        return get(tableName);
    }

    @Override
    public void createTable(String tableName) {

    }

    @Override
    public void insert(String tableName, DataSet input) {

    }

    public int getSize(String tableName) {
        return get(tableName).size();
    }

    @Override
    public Set<String> getTableNames() {
        return tables.keySet();
    }

    @Override
    public void connect(String database, String userName, String password) {
        // do nothing
    }

    public void clear(String tableName) {
        get(tableName).clear();
    }

    private List<DataSet> get(String tableName) {
        if (!tables.containsKey(tableName)) {
            tables.put(tableName, new LinkedList<DataSet>());
        }
        return tables.get(tableName);
    }

    public void create(String tableName, DataSet input) {
        get(tableName).add(input);
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (DataSet dataSet : get(tableName)) {
            if (dataSet.get("id").equals(id)) {
                dataSet.updateFrom(newValue);
            }
        }
    }

    @Override
    public void delete(String tableName, int id) {

    }

    @Override
    public void addColumn(String tableName, String columnName, String dataType) {

    }

    @Override
    public void dropColumn(String tableName, String columnName) {

    }

    @Override
    public List<String> getTableHead(String tableName) {
        return new LinkedList<>(Arrays.asList("name", "password", "id"));
    }

    public boolean isConnected() {
        return true;
    }

    public String getDatabaseName() {
        return null;
    }

    public String getUserName() {
        return null;
    }
}
