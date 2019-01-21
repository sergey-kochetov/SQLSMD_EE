package com.juja.sqlcmd_ee.service;


import com.juja.sqlcmd_ee.model.DatabaseManager;

public interface DatabaseManagerFactory {
    DatabaseManager createDatabaseManager();
}
