package com.juja.sqlcmd_ee.repos;

import com.juja.sqlcmd_ee.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);

    List<Message> findByText(String text);
}
