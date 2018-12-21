package com.juja.controller.web;

import com.juja.controller.web.Action;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NullAction implements Action {
    @Override
    public boolean canProcess(String url) {
        return false;
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) {
        // do nothing
    }

    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {

    }
}
