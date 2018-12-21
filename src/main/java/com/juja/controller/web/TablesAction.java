package com.juja.controller.web;

import com.juja.controller.web.AbstractAction;
import com.juja.service.Service;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TablesAction extends AbstractAction {

    public TablesAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/table");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String tableName = req.getParameter("table");
        req.setAttribute("table", tableName);
        req.setAttribute("tableData", service.find(getManager(req, resp), tableName));
        jsp("find", req, resp);
    }
}
