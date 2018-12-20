package com.juja.service;

import com.juja.model.DatabaseManager;
import com.juja.model.JDBCDatabaseManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManagerFactoryImpl implements DatabaseManagerFactory, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private String className;

    @Override
    public DatabaseManager createDatabaseManager() {
        return (DatabaseManager) applicationContext.getBean(className);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
