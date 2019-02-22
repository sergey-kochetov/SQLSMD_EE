package com.juja.sqlcmd_ee.service;

import com.juja.sqlcmd_ee.dao.DatabaseManager;
import com.juja.sqlcmd_ee.model.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceTableImpl implements ServiceTable {

    @Autowired
    private DatabaseManager manager;

    @Override
    public void connect(String database, String userName, String password) {
        manager.connect(database, userName, password);
    }

    @Override
    public void disconnect() {
        manager.disconnect();
    }

    @Override
    public Set<String> getTables() {
        return manager.getTables();
    }

    @Override
    public List<String> getTableHead(String tableName) {
        return manager.getTableHead(tableName);
    }

    @Override
    public List<List<Object>> getTableData(String tableName) {
        List<DataSet> tableData = manager.getTableData(tableName);
        if (tableData == null) {
            return Collections.emptyList();
        }
        LinkedList<List<Object>> list = new LinkedList<>();
        for (DataSet tableDatum : tableData) {
            List<Object> values = new LinkedList<>();
            for (Object value : tableDatum.getValues()) {
                if (value != null) {
                    values.add(value.toString());
                } else {
                    values.add("");
                }
            }
            list.add(values);
        }
        return list;
    }

    @Override
    public void createTable(String tableName) {
        manager.createTable(tableName);
    }

    @Override
    public void addColumn(String tableName, String columnName, String dataType) {
        manager.addColumn(tableName, columnName, dataType);
    }

    @Override
    public void dropColumn(String tableName, String columnName) {
        manager.dropColumn(tableName, columnName);
    }

    @Override
    public void insert(String tableName, Map<String, Object> input) {
        manager.insert(tableName, input);
    }

    @Override
    public void delete(String tableName, Long id) {
        manager.delete(tableName, id);
    }
}
