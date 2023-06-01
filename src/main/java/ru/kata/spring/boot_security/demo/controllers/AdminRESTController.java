package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServise;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminRESTController {
    private UserServise userServise;


    @Autowired
    public AdminRESTController(UserServise userServise) {
        this.userServise = userServise;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUserList() {
        List<User> list = userServise.listUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userServise.getUserById(id);
    }

    @PostMapping()
    public User addUser(@RequestBody User user) {

        return userServise.addUser(user);

    }

    @PatchMapping()
    public void editUser(@RequestBody User user) {

        userServise.updateUser(user);

    }

    @DeleteMapping("/{id}")
    public void removeUser(@PathVariable int id) {

        userServise.removeUser(id);

    }

}
