package com.juja.sqlcmd_ee.service;

import com.juja.sqlcmd_ee.domain.Customer;
import com.juja.sqlcmd_ee.domain.Role;
import com.juja.sqlcmd_ee.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private MailService mailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepo.findByUsername(username);
    }

    public boolean addCustomer(Customer customer) {
        Customer customerFromDB = customerRepo.findByUsername(customer.getUsername());

        if (customerFromDB != null) {
            return false;
        }

        customer.setActive(true);
        customer.setRoles(Collections.singleton(Role.USER));
        customer.setActivationCode(UUID.randomUUID().toString());
        customerRepo.save(customer);

        if (StringUtils.isEmpty(customer.getEmail())) {
            String msg = String.format("Hello, %s%n Welcom to Sqlsmd. " +
                    "Please, click this link: %s%s", customer.getUsername(),
                    "http://localhost:8080/activate/", customer.getActivationCode());
            mailService.send(customer.getEmail(), "Code", msg);

        }

        return true;
    }

    public boolean activateCustomer(String code) {
        Customer customer = customerRepo.findByActivationCode(code);
        if (code == null) {
            return false;
        }
        customer.setActivationCode(null);

        customerRepo.save(customer);

        return true;
    }
}
