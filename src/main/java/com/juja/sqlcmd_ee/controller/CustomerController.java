package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.domain.Customer;
import com.juja.sqlcmd_ee.domain.Role;
import com.juja.sqlcmd_ee.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String customerList(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customerList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{customer}")
    public String customerEditForm(@PathVariable Customer customer, Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("roles", Role.values());
        return "customerEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String customerSave(@RequestParam String username,
                               @RequestParam Map<String, String> form,
                               @RequestParam("customerId") Customer customer
    ) {
        customerService.saveCustomer(customer, username, form);

        return "redirect:/customer";
    }

    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal Customer customer) {
        model.addAttribute("username", customer.getUsername());
        model.addAttribute("email", customer.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(Model model,
                                @AuthenticationPrincipal Customer customer,
                                @RequestParam String password,
                                @RequestParam String email
    ) {
        customerService.updateProfile(customer, password, email);

        return "redirect:/customer/profile";
    }
}
