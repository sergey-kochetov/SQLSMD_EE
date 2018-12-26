package com.juja.service;

import com.juja.model.DatabaseManager;
import com.juja.model.UserActions;
import com.juja.model.UserActionsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public abstract class ServiceImpl implements Service {

    private List<String> commands = new LinkedList<>(Arrays.asList(
            "menu", "connect", "find", "error", "help", "success", "tables"));

    public ServiceImpl() {
    }

    public abstract DatabaseManager getManager();

    @Autowired
    private UserActionsDao userActions;

    @Override
    public List<String> commandsList() {
        return commands;
    }
    @Override
    public DatabaseManager connect(String dbName, String userName, String password) throws ServiceException {
        DatabaseManager manager = getManager();
        try {
            manager.connect(dbName, userName, password);
            userActions.log(userName, dbName, "CONNECT");
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
            //throw new ServiceException(e);
        }
        userActions.log(manager.getUserName(), manager.getDatabaseName(),
                "FIND(" + tableName + ")");
        return result;
    }

    @Override
    public void clear(DatabaseManager manager, String tableName) throws ServiceException {
        try {
            manager.clear(tableName);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Set<String> getTableNames(DatabaseManager manager) throws ServiceException {
        try {
            userActions.log(manager.getUserName(), manager.getDatabaseName(),
                    "TABLES");
            return manager.getTableNames();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<String> getTableHead(String tableName) throws ServiceException {
        try {
            return getManager().getTableHead(tableName);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Map<String, Object>> getTableData(String tableName) throws ServiceException{
        try {
            return getManager().getTableData(tableName);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(String tableName, int id, Map<String, Object> newValue) throws ServiceException {
        try {
            getManager().update(tableName, id, newValue);
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }


    @Override
    public List<UserActions> getAllFor(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cant be null or empty");
        }
        return userActions.getAllFor(userName);
    }
}
