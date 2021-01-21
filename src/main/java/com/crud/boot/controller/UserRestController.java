package com.crud.boot.controller;



import com.crud.boot.domain.User;
import com.crud.boot.repository.UserRepository;
import com.crud.boot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/users")
public class UserRestController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;
    UserRepository userRepository;

    @GetMapping
    public List<User> findAll() {
        logger.debug("Get all users");
        List<User> users = userService.listUsers();
        logger.debug("users: {}",users.toString());
        return users;
    }
    @GetMapping(name="/{id}")
    public User findById(@PathVariable int id) {
        logger.debug("Get user with id : {}",id);
        Optional<User> users = userService.userById(id);
        logger.debug("Get user  : {}",users.get());
        return users.get();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User newEmployee) {
        logger.debug("Save user : {}",newEmployee.toString());
        return userRepository.save(newEmployee);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody User newEmployee, @PathVariable( "id" ) Integer id) {

        return userRepository.findById(id)
                .map(employee -> {
                    employee.setEmail(newEmployee.getEmail());
                    employee.setLast_name(newEmployee.getLast_name());
                    employee.setUsername(newEmployee.getUsername());
                    employee.setRoles(newEmployee.getRoles());
                    employee.setPassword(newEmployee.getPassword());
                    return userRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return userRepository.save(newEmployee);
                });
    }

    @DeleteMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    void  delete(@PathVariable int id) {
        logger.debug("Delete user with id : {}",id);
        userService.delete(id);
        logger.debug("Delete user success");
    }


}
