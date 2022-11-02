package com.geraldapm.xmltesting.service;

import com.geraldapm.xmltesting.dto.ErrorMapper;
import com.geraldapm.xmltesting.dto.UserMapper;
import com.geraldapm.xmltesting.exceptions.BaseErrorException;
import com.geraldapm.xmltesting.repository.UserRepository;
import id.my.gpm.service.xmltesting.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

//    @PostConstruct
//    public void initUser() {
//        User user1 = new User();
//        user1.setName("Gerald");
//        user1.setEmpId(1);
//        user1.setSalary(123);
//
//        User user2 = new User();
//        user2.setName("Maya");
//        user2.setEmpId(2);
//        user2.setSalary(123);
//
//        User user3 = new User();
//        user3.setName("Sellina");
//        user3.setEmpId(3);
//        user3.setSalary(123);
//
//        userRepository.put(user1.getName(), user1);
//        userRepository.put(user2.getName(), user2);
//        userRepository.put(user3.getName(), user3);
//    }

    public User addNewUser(User user) {
        try {
            com.geraldapm.xmltesting.model.User userIn = userMapper.userMapToDTO(user);
            this.userRepository.save(userIn);
            return user;
        } catch (Exception err) {
            System.out.println(err);
            throw new BaseErrorException(new ErrorMapper("400","Malformed error"));
        }
    }

    public List<User> getAllUsers() {
        try {
            Iterable<com.geraldapm.xmltesting.model.User> userList = userRepository.findAll();
            List<User> userOutList = new ArrayList<>();
            for (com.geraldapm.xmltesting.model.User user: userList) {
                userOutList.add(userMapper.DTOMapToUser(user));
            }
            return userOutList;
        }  catch (Exception err) {
            System.out.println(err.getMessage());
            throw new BaseErrorException(new ErrorMapper("404","User Not Found"));
        }
    }

    public User getUserByName(String name) {
        try {
            return userMapper.DTOMapToUser(userRepository.findByName(name));
        } catch (Exception err) {
            System.out.println(err);
            throw new BaseErrorException(new ErrorMapper("404","User Not Found"));
        }
    }

    public void patchUserByName(User user) {
        try {
            User oldUser = userMapper.DTOMapToUser(userRepository.findByName(user.getName()));
            User newUser = oldUser;
            newUser.setEmpId(user.getEmpId());
            newUser.setSalary(user.getSalary());
            this.userRepository.save(userMapper.userMapToDTO(newUser));
        } catch (Exception err) {
            System.out.println(err);
            throw new BaseErrorException(new ErrorMapper("404","User Not Found"));
        }

    }

    public void deleteUserByName(String name) {
        try {
            userRepository.deleteByName(name);
        } catch (Exception err) {
            System.out.println(err);
            throw new BaseErrorException(new ErrorMapper("404","User Not Found"));
        }
    }
}
