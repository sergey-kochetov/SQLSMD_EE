package com.juja.service;

import com.juja.model.DatabaseManager;

import java.sql.SQLException;
import java.util.List;

public interface Service {
    List<String> commandsList();

    DatabaseManager connect(String dbName, String userName, String password) throws SQLException;

    List<List<String>> find(DatabaseManager manager, String tableName) throws SQLException;
}
