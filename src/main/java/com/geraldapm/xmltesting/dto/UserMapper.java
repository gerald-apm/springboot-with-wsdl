package com.geraldapm.xmltesting.dto;

import id.my.gpm.service.xmltesting.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public com.geraldapm.xmltesting.model.User userMapToDTO(User user) {
        com.geraldapm.xmltesting.model.User userOut = new com.geraldapm.xmltesting.model.User();
        userOut.setName(user.getName());
        userOut.setEmpId(user.getEmpId());
        userOut.setSalary(user.getSalary());
        return userOut;
    }

    public User DTOMapToUser(com.geraldapm.xmltesting.model.User user) {
        User userOut = new User();
        userOut.setName(user.getName());
        userOut.setEmpId(user.getEmpId());
        userOut.setSalary(user.getSalary());
        return userOut;
    }
}
