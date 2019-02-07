package com.juja.sqlcmd_ee.service;

import com.juja.sqlcmd_ee.dao.DatabaseManager;
import com.juja.sqlcmd_ee.model.DataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
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
}
