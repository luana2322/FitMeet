package com.FitMeet.repository;

import com.FitMeet.model.Chat;
import com.FitMeet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByUserName(String userName);
}
