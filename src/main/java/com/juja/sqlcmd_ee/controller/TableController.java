package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.service.ServiceTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Component
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private ServiceTable manager;

    @GetMapping
    public String getTables() {
        return "tables";
    }

    @PostMapping
    private String connectToDB(@RequestParam String database,
                               @RequestParam String username,
                               @RequestParam String password, Model model
    ) {
        manager.connect(database, username, password);
        model.addAttribute("tables", manager.getTables());
        return "tablesList";
    }

    @GetMapping("{table}")
    public String getTable(@PathVariable String table, Model model) {
        model.addAttribute("head", manager.getTableHead(table));
        model.addAttribute("data", manager.getTableData(table));
        return "tables";
    }
}
