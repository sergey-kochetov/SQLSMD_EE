package com.juja.service;

import com.juja.model.DatabaseManager;

import java.util.List;

public interface Service {
    List<String> commandsList();

    DatabaseManager connect(String dbName, String userName, String password) throws ServiceException;

    List<List<String>> find(DatabaseManager manager, String tableName);
}
