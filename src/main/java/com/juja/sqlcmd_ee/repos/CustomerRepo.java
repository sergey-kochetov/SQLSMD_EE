package com.juja.sqlcmd_ee.repos;

import com.juja.sqlcmd_ee.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Customer findByUsername(String username);

    Customer findByActivationCode(String code);
}
