package com.juja.controller.web;

import com.juja.service.Service;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditeAction extends AbstractAction {

    public EditeAction(Service service) {
        super(service);
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("/edit");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
            String element = req.getParameter("element");
            req.setAttribute("element", element);
            jsp("edit", req, resp);
    }
}
