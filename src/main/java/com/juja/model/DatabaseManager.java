package com.juja.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DatabaseManager {

    DatabaseManager NULL = new NullDatabaseManager();

    void connect(String database, String userName, String password);

    Connection getConnection();

    int getSize(String tableName);

    Set<String> getTableNames();

    List<String> getTableHead(String tableName);

    List<Map<String, Object>> getTableData(String tableName);

    void clear(String tableName);

    void drop(String tableName);

    void delete(String tableName, Map<String, Object> delValue);

    void create(String tableName, List<String> input);

    void insert(String tableName, Map<String, Object> input);

    void update(String tableName, int id, Map<String, Object> newValue);

    void disconnect();

    boolean isConnected();

    String getDatabaseName();

    String getUserName();
}
