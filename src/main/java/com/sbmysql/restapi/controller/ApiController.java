package com.sbmysql.restapi.controller;

import com.sbmysql.restapi.models.User;
import com.sbmysql.restapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return userRepo.findById(id).get();
    }

    @GetMapping("/users/email")
    public User getUserByEmail(@RequestParam String email) {
//        http://localhost:8080/users/email?email=kurt@email.com
        return userRepo.findByEmail(email);
    }

    @GetMapping("/users/name")
    public User getUserByName(@RequestParam String name) {
//        http://localhost:8080/users/name?name=Kurt
        return userRepo.findByFirstName(name);
    }

//    @GetMapping("/users/name")
//    public ResponseEntity<List<User>> getUserByName(@RequestParam String name) {
//        List username = userRepo.findByFirstName(name);
//        System.out.println("username: " + username);
//        return new ResponseEntity<List<User>>(userRepo.findByFirstName(name), HttpStatus.OK);
//    }


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
