package com.juja.sqlcmd_ee.entity;

import lombok.Data;

@Data
public class Connection {

    private String dbname;
    private String userName;
    private String password;
    private String fromPage;
}
