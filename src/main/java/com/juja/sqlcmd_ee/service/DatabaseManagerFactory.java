package com.juja.sqlcmd_ee.service;


import com.juja.sqlcmd_ee.dao.DatabaseManager;

public interface DatabaseManagerFactory {
    DatabaseManager createDatabaseManager();
}
