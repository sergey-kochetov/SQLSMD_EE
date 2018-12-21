package com.juja.model;

import com.juja.model.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class NullDatabaseManager implements DatabaseManager {
    @Override
    public void defaultConnect() throws SQLException {

    }

    @Override
    public void connect(String database, String userName, String password) throws SQLException {

    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) throws SQLException {
        return new LinkedList<>();
    }

    @Override
    public int getSize(String tableName) throws SQLException {
        return 0;
    }

    @Override
    public Set<String> getTableNames() throws SQLException {
        return new LinkedHashSet<>();
    }

    @Override
    public void clear(String tableName) throws SQLException {

    }

    @Override
    public void drop(String tableName) throws SQLException {

    }

    @Override
    public void delete(String tableName, Map<String, Object> delValue) throws SQLException {

    }

    @Override
    public void create(String tableName, List<String> input) throws SQLException {

    }

    @Override
    public void insert(String tableName, Map<String, Object> input) throws SQLException {

    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) throws SQLException {

    }

    @Override
    public List<String> getTableHead(String tableName) throws SQLException {
        return new LinkedList<>();
    }

    @Override
    public void disconnect() throws SQLException {

    }

    @Override
    public boolean isConnected() {
        return false;
    }
}
