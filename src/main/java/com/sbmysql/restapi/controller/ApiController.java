package com.sbmysql.restapi.controller;

import com.sbmysql.restapi.models.User;
import com.sbmysql.restapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiController {
    @Autowired
    UserRepo userRepo;


    @GetMapping("/")
    public String getPage() {
        return "Hello World!";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @PostMapping("/users")
    public String saveUser(@RequestBody User user) {
        userRepo.save(user);
        return "User saved";
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable long id, @RequestBody User user){
        User updatedUser = userRepo.findById(id).get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        userRepo.save(updatedUser);
        return "User updated";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable long id){
        userRepo.deleteById(id);
        return "User deleted";
    }
}
