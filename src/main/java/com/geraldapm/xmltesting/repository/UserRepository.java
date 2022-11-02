package com.geraldapm.xmltesting.repository;

import com.geraldapm.xmltesting.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByName(String name);

    void deleteByName(String name);
}
