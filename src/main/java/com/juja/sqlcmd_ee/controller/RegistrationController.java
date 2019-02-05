package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.domain.Customer;
import com.juja.sqlcmd_ee.domain.Role;
import com.juja.sqlcmd_ee.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerRepo customerRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String newUser(Customer customer, Model model) {
        Customer user = customerRepo.findByUsername(customer.getUsername());

        if (user != null) {
            model.addAttribute("message", "User exists");
            return "registration";
        }

        customer.setActive(true);
        customer.setRoles(Collections.singleton(Role.USER));
        customerRepo.save(customer);

        return "redirect:/login";
    }
}
