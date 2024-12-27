package com.FitMeet.service;

import com.FitMeet.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int theId);

    User findByUserName(String userName);

    void save(User theUser);

    void deleteById(int theId);
}
