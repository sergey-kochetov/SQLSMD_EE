package com.juja.sqlcmd_ee.controller;

import com.juja.sqlcmd_ee.domain.Customer;
import com.juja.sqlcmd_ee.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String newUser(
            @RequestParam("password2") String password2,
            @Valid Customer customer,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isPassword2Empty = StringUtils.isEmpty(password2);
        if (isPassword2Empty) {
            model.addAttribute("password2Error", "Password confirmation can't be empty");
        }
        if (customer.getPassword() != null && customer.getPassword().equals(password2)) {
            model.addAttribute("passwordError", "Password are different");
        }
        if (isPassword2Empty || bindingResult.hasErrors()) {
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
            model.addAttribute("messageType", "succes");
        } else {
            model.addAttribute("message", "code is not found");
            model.addAttribute("messageType", "danger");
        }
        return "login";
    }
}
