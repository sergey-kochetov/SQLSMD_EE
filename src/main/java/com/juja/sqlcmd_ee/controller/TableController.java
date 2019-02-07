package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.service.ServiceTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RequestMapping("/tables")
public class TableController {

    @Autowired
    private ServiceTable serviceTable;

    private boolean isConnect = false;

    @GetMapping
    public String getTables(Model model) {
        model.addAttribute("connect", isConnect);
        if (isConnect) {
            model.addAttribute("tables", serviceTable.getTables());
        }
        return "tables";
    }

    @PostMapping("/connect")
    private String connectToDB(@RequestParam String database,
                               @RequestParam String username,
                               @RequestParam String password, Model model
    ) {
        serviceTable.connect(database, username, password);
        isConnect = true;
        model.addAttribute("connect", isConnect);
        return "redirect:/tables";
    }

    @PostMapping("/disconnect")
    private String disconnectToDB(Model model) {
        serviceTable.disconnect();
        isConnect = false;
        model.addAttribute("connect", isConnect);
        return "tables";
    }

    @GetMapping("{table}")
    public String getTable(@PathVariable String table, Model model) {
        model.addAttribute("head", serviceTable.getTableHead(table));
        List<List<Object>> tableData = serviceTable.getTableData(table);
        model.addAttribute("datas", tableData);
        return "tablesData";
    }
}
