package com.juja.controller.web;

import com.juja.model.DatabaseManager;
import com.juja.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    @Autowired
    private Service service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        DatabaseManager manager = (DatabaseManager) req.getSession().getAttribute("db_manager");
        try {
            if (action.startsWith("/connect")) {
                if (manager == null) {
                    jsp("connect", req, resp);
                } else {
                    resp.sendRedirect(resp.encodeRedirectURL("menu"));
                }
                return;
            }
            if (manager == null) {
                resp.sendRedirect(resp.encodeRedirectURL("connect"));
                return;
            }

            if (action.startsWith("/menu") || action.equals("/")) {
                req.setAttribute("items", service.commandsList());
                jsp("menu", req, resp);
            } else if (action.startsWith("/help")) {
                req.getRequestDispatcher("help.jsp").forward(req, resp);
            } else if (action.startsWith("/find")) {
                String tableName = req.getParameter("table");

                req.setAttribute("table", service.find(manager, tableName));
                jsp("find", req, resp);
            } else if (action.startsWith("/tables")) {
                String tableName = req.getParameter("table");

                req.setAttribute("tables", manager.getTableNames());

                jsp("tables",req, resp);
            } else if (action.startsWith("/")) {
                String tableName = action.replace("/","");
                req.setAttribute("table", service.find(manager, tableName));
                jsp("find", req, resp);
            } else {
                jsp("error", req, resp);
            }
        } catch (Exception e) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        try {
            if (action.startsWith("/connect")) {
                String dbName = req.getParameter("dbname");
                String userName = req.getParameter("username");
                String password = req.getParameter("password");
                DatabaseManager manager = service.connect(dbName, userName, password);
                req.getSession().setAttribute("db_manager", manager);
                resp.sendRedirect(resp.encodeRedirectURL("menu"));
            }
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            jsp("error", req, resp);
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length(), requestURI.length());
    }

    private void jsp(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(jsp + ".jsp").forward(request, response);
    }
}
