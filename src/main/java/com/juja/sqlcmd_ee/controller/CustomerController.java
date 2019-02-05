package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping
    public String customerList(Model model) {
        model.addAttribute("customers", customerRepo.findAll());
        return "customerList";
    }
}
