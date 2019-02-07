package com.juja.sqlcmd_ee.service;

import com.juja.sqlcmd_ee.dao.DatabaseManager;
import com.juja.sqlcmd_ee.model.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ServiceTableImpl implements ServiceTable {

    @Autowired
    private DatabaseManager manager;

    @Override
    public void connect(String database, String userName, String password) {
        manager.connect(database, userName, password);
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
    public List<DataSet> getTableData(String tableName) {
        return manager.getTableData(tableName);
    }
}
