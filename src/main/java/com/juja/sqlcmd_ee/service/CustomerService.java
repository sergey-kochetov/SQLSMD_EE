package com.juja.sqlcmd_ee.service;

import com.juja.sqlcmd_ee.domain.Customer;
import com.juja.sqlcmd_ee.domain.Role;
import com.juja.sqlcmd_ee.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepo.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return customer;
    }

    public boolean addCustomer(Customer customer) {
        Customer customerFromDB = customerRepo.findByUsername(customer.getUsername());

        if (customerFromDB != null) {
            return false;
        }

        customer.setActive(true);
        customer.setRoles(Collections.singleton(Role.USER));
        customer.setActivationCode(UUID.randomUUID().toString());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customerRepo.save(customer);

        sendMsg(customer);

        return true;
    }

    private void sendMsg(Customer customer) {
        if (StringUtils.isEmpty(customer.getEmail())) {
            String msg = String.format("Hello, %s%n Welcom to Sqlsmd. " +
                    "Please, click this link: %s%s", customer.getUsername(),
                    "http://localhost:8080/activate/", customer.getActivationCode());
            mailService.send(customer.getEmail(), "Code", msg);

        }
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

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public void saveCustomer(Customer customer, String username, Map<String, String> form) {
        customer.setUsername(username);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        customer.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                customer.getRoles().add(Role.valueOf(key));
            }
        }
        customerRepo.save(customer);
    }

    public void updateProfile(Customer customer, String password, String email) {
        String oldEmail = customer.getEmail();

        if (checkEmails(email, oldEmail)) {
            customer.setEmail(email);
            if (!StringUtils.isEmpty(email)) {
                customer.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!StringUtils.isEmpty(password)) {
            customer.setPassword(password);
        }
        customerRepo.save(customer);
        if (checkEmails(email, oldEmail)) {
            sendMsg(customer);
        }
    }

    private boolean checkEmails(String email, String oldEmail) {
        return (email != null && !email.equals(oldEmail)) ||
                (oldEmail != null && !oldEmail.equals(email));
    }
}
