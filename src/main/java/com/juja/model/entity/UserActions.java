package com.juja.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_actions", schema = "public")
public class UserActions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "db_name")
    private String dbName;
//    @Column(name = "database_id")
//    private Database database;

    @Column(name = "action")
    private String action;

    public UserActions() {
        // do nothing
    }

    public UserActions(String userName, String dbName, String action) {
        this.userName = userName;
        this.dbName = dbName;
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
