package com.juja.controller.web;

import com.juja.controller.web.AbstractAction;
import com.juja.controller.web.Action;
import com.juja.service.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MenuAction extends AbstractAction {

    private List<Action> actions;

    public MenuAction(Service service, List<Action> actions) {
        super(service);
        this.actions = actions;
    }

    @Override
    public boolean canProcess(String url) {
        return url.equalsIgnoreCase("/menu") || url.equals("/");
    }

    @Override
    public void get(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("items", actions);
        jsp("menu", req, resp);
    }
}
