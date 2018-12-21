package com.juja.controller.web;

import com.juja.controller.web.AbstractAction;
import com.juja.model.DatabaseManager;
import com.juja.service.Service;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConnectAction extends AbstractAction {

    public ConnectAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.equalsIgnoreCase("/connect");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        jsp("connect", req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String dbName = req.getParameter("dbname");
        String userName = req.getParameter("username");
        String password = req.getParameter("password");

        DatabaseManager manager = service.connect(dbName, userName, password);
        req.getSession().setAttribute("db_manager", manager);

        redirect(resp, "menu");
    }
}
