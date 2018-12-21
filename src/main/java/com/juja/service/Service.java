package com.juja.service;

import com.juja.model.DatabaseManager;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Service {
    List<String> commandsList();

    DatabaseManager connect(String dbName, String userName, String password) throws ServiceException;

    List<List<String>> find(DatabaseManager manager, String tableName) throws ServiceException;

    void clear(DatabaseManager manager, String tableName) throws ServiceException;

    Set<String> getTableNames() throws ServiceException;

    List<String> getTableHead(String tableName) throws ServiceException;

    List<Map<String, Object>> getTableData(String tableName) throws ServiceException;

    void update(String tableName, int id, Map<String, Object> newValue) throws ServiceException;
}
