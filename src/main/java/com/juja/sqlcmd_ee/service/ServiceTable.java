package com.juja.sqlcmd_ee.service;

import com.juja.sqlcmd_ee.model.DataSet;

import java.util.List;
import java.util.Set;

public interface ServiceTable {

    void connect(String database, String userName, String password);

    void disconnect();

    Set<String> getTables();

    List<String> getTableHead(String tableName);

    List<List<Object>> getTableData(String tableName);
}
