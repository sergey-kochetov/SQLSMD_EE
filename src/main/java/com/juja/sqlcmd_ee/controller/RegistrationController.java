package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.domain.Customer;
import com.juja.sqlcmd_ee.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String newUser(@Valid Customer customer, BindingResult bindingResult, Model model) {
        if (customer.getPassword() != null && customer.getPassword().equals(customer.getPassword2())) {
            model.addAttribute("passwordError", "Password are different");
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtil.getErrors(bindingResult);
            model.addAttribute(errors);

            return "registration";
        }

        if (!customerService.addCustomer(customer)) {
            model.addAttribute("usernameError", "User exists");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = customerService.activateCustomer(code);

        if (isActivated) {
            model.addAttribute("message", "successful");
        } else {
            model.addAttribute("message", "code is not found");
        }
        return "login";
    }
}
