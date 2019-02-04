package com.juja.sqlcmd_ee.controller;

import org.springframework.stereotype.Controller;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/")
    public String view(@RequestParam(value = "name", required = false, defaultValue = "noname")
                                   String name, Map<String, Object> model) {
        model.put("name", "Привет " + name);
        return "index";
    }

}
