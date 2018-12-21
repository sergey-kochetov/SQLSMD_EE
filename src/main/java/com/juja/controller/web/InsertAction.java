package com.juja.controller.web;

import com.juja.controller.web.AbstractAction;
import com.juja.service.Service;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InsertAction extends AbstractAction {

    public InsertAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/insert");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        jsp("insert",req, resp);
    }
}
