package com.crud.boot.controller;

import com.crud.boot.domain.Role;
import com.crud.boot.domain.User;
import com.crud.boot.repository.RoleRepository;
import com.crud.boot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class RoleRestController {

    @Autowired
    RoleRepository roleRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public List<Role> findAll() {
        logger.info("Get all roles");
        List<Role> roles = roleRepository.findAll();
        logger.info("roles: {}",roles.toString());
        return roles;
    }
}
