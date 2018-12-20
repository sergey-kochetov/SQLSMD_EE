package com.juja.service;

import com.juja.model.DatabaseManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class ServiceImpl implements Service, ApplicationContextAware {

    private List<String> commands;

    private ApplicationContext context;

    public ServiceImpl() {
    }

    @Override
    public List<String> commandsList() {
        //return Arrays.asList("help", "menu", "connect", "find");
        return commands;
    }
    @Override
    public DatabaseManager connect(String dbName, String userName, String password) throws SQLException {
        DatabaseManager manager =getManager();
        manager.connect(dbName, userName, password);
        return manager;
    }

    @Override
    public List<List<String>> find(DatabaseManager manager, String tableName) throws SQLException {
        List<List<String>> result = new LinkedList<>();
        List<String> tableColumns = new LinkedList<>(manager.getTableColumns(tableName));
        Set<Map<String, Object>> tableData = manager.getTableData(tableName);

        result.add(tableColumns);

        for (Map<String, Object> map : tableData) {
            List<String> current = new LinkedList<>();
            map.entrySet()
                    .stream()
                    .map(e -> current.add(e.getValue().toString()));
            result.add(current);
        }
        return result;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    @Bean
    public DatabaseManager getManager() {
        return (DatabaseManager) context.getBean("JDBCDatabaseManager");
    }
}
