package com.juja.sqlcmd_ee.controller;

public class Connection {

    private String dbname;
    private String userName;
    private String password;
    private String fromPage;

    public Connection(String page) {
        this.fromPage = page;
    }

    public Connection() {
    }

    public String getDbName() {
        return dbname;
    }

    public void setDbName(String dbname) {
        this.dbname = dbname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFromPage() {
        return fromPage;
    }

    public void setFromPage(String fromPage) {
        this.fromPage = fromPage;
    }
}
