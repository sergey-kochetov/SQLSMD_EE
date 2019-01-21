package com.juja.model;

import com.juja.model.entity.UserAction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserActionRepository extends CrudRepository<UserAction, Integer> {
    List<UserAction> findByUserName(String userName);
}
