package com.crud.boot.controller;



import com.crud.boot.domain.Role;
import com.crud.boot.domain.User;
import com.crud.boot.repository.RoleRepository;
import com.crud.boot.repository.UserRepository;
import com.crud.boot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class UserRestController {

   Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public List<User> findAll() {
        logger.info("Get all users");
        List<User> users = userService.listUsers();
        logger.info("users: {}",users.toString());
        return users;
    }
    @RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public User update(@RequestBody User newEmployee, @PathVariable( "id" ) Integer id) {
        String pasword;
        if(newEmployee.getPassword().equals("")){
            Optional<User> user = userRepository.findById(id);
            pasword = user.get().getPassword();

        }else{
            pasword = newEmployee.getPassword();
        }
        newEmployee.setPassword(pasword);
        Set<Role> roles = new HashSet<>();
        roles.addAll(newEmployee.getRoles());
        return userRepository.findById(id)
                .map(employee -> {
                    employee.setEmail(newEmployee.getEmail());
                    employee.setLast_name(newEmployee.getLast_name());
                    employee.setUsername(newEmployee.getUsername());
                    employee.setRoles(roles);
                    employee.setPassword(newEmployee.getPassword());
                    return userRepository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return userRepository.save(newEmployee);
                });
    }
    @RequestMapping(value = { "/" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User newEmployee) {
        logger.info("Save user : {}",newEmployee.toString());
        logger.info("Roles : {}",newEmployee.getRoles());
        Set<Role> roles = new HashSet<>();
        for (Role role:newEmployee.getRoles()){
            Optional<Role> r = roleRepository.findById(role.getId());
            roles.add(r.get());
        }
        String password = newEmployee.getPassword();
        newEmployee.setPassword(new BCryptPasswordEncoder(12).encode(password));
        newEmployee.setRoles(roles);
        return userRepository.save(newEmployee);
    }

    @RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
    public User findById(@PathVariable int id) {
        logger.info("Get user with id : {}",id);
        Optional<User> users = userService.userById(id);
        logger.info("Get user  : {}",users.get());
        return users.get();
    }

    @RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    void  delete(@PathVariable int id) {
        logger.info("Delete user with id : {}",id);
        userService.delete(id);
        logger.info("Delete user success");
    }
}
