package com.juja.controller.web;

import com.juja.controller.web.AbstractAction;
import com.juja.service.Service;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class ClearAction extends AbstractAction {
    public ClearAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/clear");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String tableName = req.getParameter("tableName");
        service.clear(getManager(req, resp), tableName);
        jsp("clear",req, resp);
        forwardToSuccess(String.format("Table '%s' is cleared", tableName), req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String tableName = req.getParameter("tableName");
        try {
            getManager(req, resp).clear(tableName);
        } catch (SQLException e) {
            throw new ServletException();
        }
        redirect(resp, "success");
    }
}
