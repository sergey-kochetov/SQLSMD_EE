package com.juja.service;

import com.juja.model.DatabaseManager;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public DatabaseManager connect(String dbName, String userName, String password) throws ServiceException {
        DatabaseManager manager = getManager();
        try {
            manager.connect(dbName, userName, password);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return manager;
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) throws ServiceException {
        List<List<String>> result = new LinkedList<>();
        try {
            List<String> tableColumns = manager.getTableHead(tableName);
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
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public void clear(DatabaseManager manager, String tableName) throws ServiceException {
        try {
            manager.clear(tableName);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<String> getTableNames() throws ServiceException{
        try {
            return getManager().getTableNames();
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getTableHead(String tableName) throws ServiceException {
        try {
            return getManager().getTableHead(tableName);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) throws ServiceException{
        try {
            return getManager().getTableData(tableName);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) throws ServiceException {
        try {
            getManager().update(tableName, id, newValue);
        } catch (SQLException e) {
            throw new ServiceException(e);
        }
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
