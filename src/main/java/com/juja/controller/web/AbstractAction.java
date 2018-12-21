package com.juja.controller.web;

import com.juja.model.DatabaseManager;
import com.juja.service.Service;
import com.juja.service.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractAction implements Action {
    protected Service service;

    public AbstractAction(Service service) {
        this.service = service;
    }

    protected void jsp(String jsp, ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(jsp + ".jsp").forward(request, response);
    }
    protected void redirect(HttpServletResponse resp, String url) throws IOException {
        resp.sendRedirect(resp.encodeRedirectURL(url));
    }
    protected DatabaseManager getManager(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("db_manager");
        if (manager != null) {
            return manager;
        } else {
            redirect(resp, "connect");
            return DatabaseManager.NULL;
        }
    }

    protected void forwardToSuccess(String msg, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", msg);
        jsp("success", req, resp);
    }

    protected void forwardToError(ServiceException e, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", e.getMessage());
        jsp("error", req, resp);
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        // do nothing
    }
    @Override
    public void post(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServiceException {
        // do nothing
    }
}
