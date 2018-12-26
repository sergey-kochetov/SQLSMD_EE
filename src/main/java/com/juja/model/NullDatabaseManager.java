package com.juja.model;

import com.juja.model.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class NullDatabaseManager implements DatabaseManager {

    @Override
    public void connect(String database, String userName, String password) {

    }

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) {
        return new LinkedList<>();
    }

    @Override
    public int getSize(String tableName) {
        return 0;
    }

    @Override
    public Set<String> getTableNames() {
        return new LinkedHashSet<>();
    }

    @Override
    public void clear(String tableName) {

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

    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) {

    }

    @Override
    public List<String> getTableHead(String tableName) {
        return new LinkedList<>();
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
        return "";
    }

    @Override
    public String getUserName() {
        return "";
    }
}
