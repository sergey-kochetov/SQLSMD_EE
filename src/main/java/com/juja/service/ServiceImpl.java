package com.juja.service;

import com.juja.model.DatabaseManager;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public abstract class ServiceImpl implements Service {

    private List<String> commands;

    public ServiceImpl() {
    }

    public abstract DatabaseManager getManager();

    @Override
    public List<String> commandsList() {
        return commands;
    }
    @Override
    public DatabaseManager connect(String dbName, String userName, String password) {
        DatabaseManager manager = getManager();
        try {
            manager.connect(dbName, userName, password);
        } catch (Exception e) {

        }
        return manager;
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) {
        List<List<String>> result = new LinkedList<>();
        try {
            List<String> tableColumns = manager.getTableColumns(tableName);
            List<Map<String, Object>> tableData = manager.getTableData(tableName);

            result.add(tableColumns);
            for (Map<String, Object> map : tableData) {
                List<String> current = new LinkedList<String>();
                for (Object value : map.values()) {
                    current.add(value.toString());
                }
                result.add(current);
            }
        } catch (Exception e) {

        }
        return result;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
