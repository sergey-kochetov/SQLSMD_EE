package com.juja.controller;

import com.juja.model.DatabaseManager;
import com.juja.service.Service;
import com.juja.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MainController {

    @Autowired
    private Service service;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String main() {
        return "redirect:/help";
    }

    @RequestMapping(value = "/help", method = RequestMethod.GET)
    public String help() {
        return "help";
    }

    @RequestMapping(value = "/find", params = { "table" }, method = RequestMethod.GET)
    public String find(@RequestParam(value = "table") String tableName,
                       HttpSession session, Model model) throws ServiceException {
        DatabaseManager manager = getManager(session);
        if (manager == null) {
            session.setAttribute("from-page", "/find?table=" + tableName);
            return "redirect:connect";
        }
        model.addAttribute("tableData", service.find(manager, tableName));
        return "find";
    }

    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(HttpSession session, Model model) {
        DatabaseManager manager = getManager(session);
        String page = (String) session.getAttribute("from-page");
        session.removeAttribute("from-page");
        model.addAttribute("connection", new Connection(page));

        if (manager == null) {
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
            DatabaseManager manager = service.connect(
                    connection.getDbname(),
                    connection.getUsername(),
                    connection.getPassword());
            session.setAttribute("db_manager", manager);
            return "redirect:" + connection.getFromPage();
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", e.getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public String menu(Model model) {
        model.addAttribute("items", service.commandsList());
        return "menu";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String error() {
        return "error";
    }
    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public String tables(Model model, HttpSession session) throws ServiceException {
        DatabaseManager manager = getManager(session);

        if (manager == null) {
           session.setAttribute("from-page", "/tables");
            return "redirect:/connect";
        }
        model.addAttribute("tables", service.getTableNames(manager));

        return "tables";
    }

    private DatabaseManager getManager(HttpSession session) {
        return (DatabaseManager) session.getAttribute("db_manager");
    }

}
