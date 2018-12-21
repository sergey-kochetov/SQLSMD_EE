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
        try {
            if (action.equals("/connect")) {
                if (getManager(req) == null) {
                    jsp("connect", req, resp);
                } else {
                    resp.sendRedirect(resp.encodeRedirectURL("menu"));
                }
                return;
            }
            if (getManager(req) == null) {
                resp.sendRedirect(resp.encodeRedirectURL("connect"));
                return;
            }

            if (action.startsWith("/menu") || action.equals("/")) {
                req.setAttribute("items", service.commandsList());
                jsp("menu", req, resp);
            } else if (action.startsWith("/help")) {
                jsp("help", req, resp);
            } else if (action.startsWith("/find")) {
                String tableName = req.getParameter("table");
                req.setAttribute("table", tableName);
                req.setAttribute("tableData", service.find(getManager(req), tableName));
                jsp("find", req, resp);
            } else if (action.startsWith("/tables")) {
                req.setAttribute("tables", getManager(req).getTableNames());
                jsp("tables",req, resp);
            }  else if (action.startsWith("/clear")) {
                jsp("clear",req, resp);
            }  else if (action.startsWith("/drop")) {
                jsp("drop",req, resp);
            }  else if (action.startsWith("/success")) {
                jsp("success",req, resp);
            }  else {
                jsp("error", req, resp);
            }
        } catch (Exception e) {
            jsp("error", req, resp);
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
            } else if (action.startsWith("/clear")) {
                String tableName = req.getParameter("tableName");
                getManager(req).clear(tableName);
                resp.sendRedirect(resp.encodeRedirectURL("success"));
            } if (action.startsWith("/drop")) {
                String tableName = req.getParameter("tableName");
                getManager(req).drop(tableName);
                resp.sendRedirect(resp.encodeRedirectURL("success"));
            }
        } catch (Exception e) {
            req.setAttribute("message", e.getMessage());
            jsp("error", req, resp);
        }
    }

    private DatabaseManager getManager(HttpServletRequest req) {
        return (DatabaseManager) req.getSession().getAttribute("db_manager");
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length(), requestURI.length());
    }

    private void jsp(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(jsp + ".jsp").forward(request, response);
    }
}
