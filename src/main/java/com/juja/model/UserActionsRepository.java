package com.juja.model;

import com.juja.model.entity.UserActions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserActionsRepository extends CrudRepository<UserActions, Integer> {

    List<UserActions> findByUserName(String userName);
}
