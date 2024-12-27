package com.FitMeet.controller;

import com.FitMeet.model.User;
import com.FitMeet.repository.UserRepository;
import com.FitMeet.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserRestController(UserService theUserService) {
        userService = theUserService;
    }


    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/finduserbyid")
    public ResponseEntity<User> findById(@RequestParam("userId") int coachId) {
        User user= userService.findById(coachId);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/finduserbyname")
    public ResponseEntity<User> findByName(@RequestParam("userName") String coachId) {
        User user= userRepository.findByUserName(coachId);
        return ResponseEntity.ok(user);
    }


    // mapping for adding a user, post method
    @PostMapping("/users/post")
    public User addUser(@RequestBody User theUser) {

        //before adding a user, check if username already exist
        if (userService.findByUserName(theUser.getUserName()) != null) {
            return User.USER_NAME_TAKEN;
        } else {
            theUser.setId(0);
            theUser.setImageURL("null");
            userService.save(theUser);
            return theUser;
        }

    }

    // mapping for delete user with id

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable int userId) {

        User tempUser = userService.findById(userId);

        if (tempUser == null) {

            throw new RuntimeException(("The item to be deleted not found - " + userId));
        }
        userService.deleteById(userId);

        return "Deleted user id - " + userId;
    }


    // mapping for put, update existing users

    @PutMapping("/users")
    public User updateUser(@RequestBody User theUser) {

        userService.save(theUser);
        return theUser;
    }

}
