package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.model.DatabaseManager;
import com.juja.sqlcmd_ee.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private Service service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "redirect:/menu";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session, Model model) {
        String page = (String) session.getAttribute("from-page");
        session.removeAttribute("from-page");
        model.addAttribute("connection", new Connection(page));

        if (getManager(session) == null) {
            return "connect";
        } else {
            return "menu";
        }
    }

    @RequestMapping(value = "/connect", method = RequestMethod.POST)
    public String connecting(@ModelAttribute("connection") Connection connection,
                             HttpSession session, Model model)
    {
        try {
            DatabaseManager manager = service.connect(connection.getDbName(),
                    connection.getUserName(), connection.getPassword());
            session.setAttribute("db_manager", manager);
            return "redirect:" + connection.getFromPage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/tables/{table}", method = RequestMethod.GET)
    public String tables(Model model,
                         @PathVariable(value = "table") String table,
                         HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (manager == null) {
            session.setAttribute("from-page", "/tables/" + table);
            return "redirect:/connect";
        }

        model.addAttribute("table", service.find(manager, table));

        return "find";
    }

    @RequestMapping(value = "/actions/{userName}", method = RequestMethod.GET)
    public String actions(Model model,
                          @PathVariable(value = "userName") String userName) {
        model.addAttribute("actions", service.getAllFor(userName));

        return "actions";
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(Model model) {
        model.addAttribute("items", service.commandsList());
        return "menu";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, HttpSession session) {
        DatabaseManager manager = getManager(session);

        if (manager == null) {
            session.setAttribute("from-page", "/list");
            return "redirect:/connect";
        }

        model.addAttribute("list", service.tables(manager));
        return "list";
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }

    // TODO со странички http://localhost:8080/sqlcmd/tables/user
    // мереходим на menu то видим ошибку
}
