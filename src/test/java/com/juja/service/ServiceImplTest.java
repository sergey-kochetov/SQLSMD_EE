package com.juja.service;

import com.juja.model.DatabaseManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test-app-context.xml")
public class ServiceImplTest {
    @Autowired
    private Service service;
    private DatabaseManager manager;

    @Before
    public void shouldConnect() throws ServiceException {
       manager = service.connect("sqlsmd", "postgres", "postgres");
    }

    @Test
    public void shouldGetTableData() throws ServiceException, SQLException {
       manager.clear("customer");

    }
}
