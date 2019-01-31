package com.juja.sqlcmd_ee.model;

import com.juja.sqlcmd_ee.dao.DatabaseManager;
import com.juja.sqlcmd_ee.dao.JDBCDatabaseManager2;

/**
 * Created by indigo on 25.08.2015.
 */
public class JDBCDatabaseManagerTest extends DatabaseManagerTest {

    @Override
    public DatabaseManager getDatabaseManager() {
        return new JDBCDatabaseManager2();
    }
}
