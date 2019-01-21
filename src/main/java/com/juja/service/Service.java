package com.juja.service;

import com.juja.model.DatabaseManager;
import com.juja.model.entity.UserAction;

import java.util.List;
import java.util.Set;

public interface Service {

    List<String> commandsList();

    DatabaseManager connect(String databaseName, String userName, String password);

    List<List<String>> find(DatabaseManager manager, String tableName);

    Set<String> tables(DatabaseManager manager);

    List<UserAction> getAllFor(String userName);
}
