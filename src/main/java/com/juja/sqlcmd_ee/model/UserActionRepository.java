package com.juja.sqlcmd_ee.model;

import com.juja.sqlcmd_ee.model.entity.UserAction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserActionRepository extends CrudRepository<UserAction, Integer> {
    List<UserAction> findByUserName(String userName);
}
