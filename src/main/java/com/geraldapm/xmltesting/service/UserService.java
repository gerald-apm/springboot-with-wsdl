package com.geraldapm.xmltesting.service;

import id.my.gpm.service.xmltesting.AddUserRequest;
import id.my.gpm.service.xmltesting.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByName(String name);

    void patchUserByName(User user);

    void deleteUserByName(String name);

    User addNewUser(User user);
}
