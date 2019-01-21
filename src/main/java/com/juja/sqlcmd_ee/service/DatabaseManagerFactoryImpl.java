package com.juja.sqlcmd_ee.service;

import com.juja.sqlcmd_ee.model.DatabaseManager;

public class DatabaseManagerFactoryImpl implements DatabaseManagerFactory {

    private String className;

    @Override
    public DatabaseManager createDatabaseManager() {
        try {
            return (DatabaseManager)Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
