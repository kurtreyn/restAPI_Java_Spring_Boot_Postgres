package com.sbmysql.restapi.controller;

import com.sbmysql.restapi.entities.UserEntity;
import com.sbmysql.restapi.models.UserModel;
import com.sbmysql.restapi.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {
    UserRepo userRepo;

    public ApiController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public String getPage() {
        return "Server is running";
    }

    @GetMapping("/wakeup")
    public ResponseEntity<HashMap<String, String>> wakeup() {
        HashMap<String, String> response = new HashMap<>();
        response.put("status", "ready");
        System.out.println("response: " + response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserModel>> getUsers() {
        List<UserEntity> users = userRepo.findAll();
        List<UserModel> userModels = new ArrayList<>();
        for (UserEntity user : users) {
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            userModels.add(userModel);
        }
        return new ResponseEntity<List<UserModel>>(userModels, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable long id) {
        UserEntity user = userRepo.findById(id).get();
        UserModel userModel = new UserModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setEmail(user.getEmail());
        return new ResponseEntity<>(userModel, HttpStatus.OK);
    }

    @GetMapping("/users/email")
    public ResponseEntity<UserModel> getUserByEmail(@RequestParam String email) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepo.findByEmail(email));
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserEntity user = userOptional.get();
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
    }

    @GetMapping("/users/name")
    public ResponseEntity<UserModel> getUserByName(@RequestParam String name) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepo.findByName(name));
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            UserEntity user = userOptional.get();
            UserModel userModel = new UserModel();
            userModel.setId(user.getId());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            return new ResponseEntity<>(userModel, HttpStatus.OK);
        }
    }


    @PostMapping("/signup")
    public ResponseEntity saveUser(@RequestBody UserModel user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userRepo.save(userEntity);
        System.out.println("saved user: " + user.getName());
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity loginUser(@RequestBody UserModel user) {
        UserEntity userEntity = new UserEntity();
        String givenEmail = user.getEmail();
        String givenPassword = user.getPassword();
        userEntity = userRepo.findByEmail(givenEmail);
        System.out.println(userEntity);
        if (userEntity == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        } else {
            String actualPassword = userEntity.getPassword();
            if (givenPassword.equals(actualPassword)) {
                System.out.println("logging user in: " + user.getName());
                return new ResponseEntity(user, HttpStatus.OK);
            } else {
                return new ResponseEntity("Incorrect password", HttpStatus.UNAUTHORIZED);
            }
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity updateUser(@PathVariable long id, @RequestBody UserModel user){
        UserEntity updatedUser = userRepo.findById(id).get();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        userRepo.save(updatedUser);
        return new ResponseEntity(user, HttpStatus.OK);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        try {
            if(userRepo.findById(id).isPresent()) {
                userRepo.deleteById(id);
                return new ResponseEntity(HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
