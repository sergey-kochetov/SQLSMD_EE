package com.juja.controller.web;

import com.juja.controller.util.UtilsCommand;
import com.juja.controller.web.AbstractAction;
import com.juja.service.Service;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UpdateAction extends AbstractAction {
    public UpdateAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/update");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        String tableName = req.getParameter("tableName");
        int id = Integer.parseInt(req.getParameter("id"));
        List<String> columnName = service.getTableHead(tableName);
        req.setAttribute("columnName", columnName);
        //req.setAttribute("items", service.update(tableName, id, service.getTableData(tableName)););
        service.clear(getManager(req, resp), tableName);
        jsp("clear",req, resp);
        forwardToSuccess(String.format("Table '%s' is update", tableName), req, resp);
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String[] paramName = req.getParameterValues("name[]");
        Object[] paramValue = req.getParameterValues("value[]");
        String tableName = req.getParameter("tableName");

        Map<String, Object> newValue = UtilsCommand.getDataMap();
        for (int i = 0; i < paramName.length; i++) {
            newValue.put(paramName[i], paramValue[i]);
        }

        try {
            service.update(tableName, id, newValue);
            forwardToSuccess(String.format("Updated '%s' success", tableName), req, resp);
        } catch (ServiceException e) {
            forwardToError(e, req, resp);
        }
    }
}
