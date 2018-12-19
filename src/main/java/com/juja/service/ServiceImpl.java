package com.juja.service;

import com.juja.model.DatabaseManager;
import com.juja.model.JDBCDatabaseManager;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceImpl implements Service {

    @Override
    public List<String> commandsList() {
        return Arrays.asList("help", "menu", "connect", "find");
    }

    @Override
    public DatabaseManager connect(String dbName, String userName, String password) throws SQLException {
        DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(dbName, userName, password);
        return manager;
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) throws SQLException {
        List<List<String>> result = new LinkedList<>();
        List<String> tableColumns = new LinkedList<>(manager.getTableColumns(tableName));
        Set<Map<String, Object>> tableData = manager.getTableData(tableName);

        result.add(tableColumns);

        for (Map<String, Object> map : tableData) {
            List<String> current = new LinkedList<>();
            map.entrySet()
                    .stream()
                    .map(e -> current.add(e.getValue().toString()));
            result.add(current);
        }
        return result;
    }
}
