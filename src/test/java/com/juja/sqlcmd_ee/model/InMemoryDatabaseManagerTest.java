package com.juja.sqlcmd_ee.model;

import com.juja.sqlcmd_ee.dao.DatabaseManager;

public class InMemoryDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new InMemoryDatabaseManager();
    }
}
