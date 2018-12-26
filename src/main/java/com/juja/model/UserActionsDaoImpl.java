package com.juja.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserActionsDaoImpl implements UserActionsDao {

    private JdbcTemplate template;

    public UserActionsDaoImpl() {}

    @Autowired
    @Qualifier(value = "logDataSource")
    public void setDataSource(DataSource dataSource) {
        template = new JdbcTemplate(dataSource);
    }

    @Override
    public void log(String userName, String dbName, String action) {
        template.update("INSERT INTO public.user_actions (user_name, db_name, action) VALUES (?,?,?)",
                userName, dbName, action);
    }

    @Override
    public List<UserActions> getAllFor(String userName) {
        return template.query("SELECT * FROM public.user_actions" +
                "WHERE user_name=?",
                new String[] { userName },
                new RowMapper<UserActions>() {
            @Override
            public UserActions mapRow(ResultSet rs, int i) throws SQLException {
                UserActions result = new UserActions();
                result.setId(rs.getInt("id"));
                result.setUserName(rs.getString("user_name"));
                result.setDbName(rs.getString("db_name"));
                result.setAction(rs.getString("action"));
                return result;
            }
        });
    }
}
