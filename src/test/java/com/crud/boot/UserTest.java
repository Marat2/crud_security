package com.crud.boot;


import com.crud.boot.controller.UserRestController;
import com.crud.boot.domain.User;
import com.crud.boot.services.UserService;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.*;

@SpringBootTest
public class UserTest {

    @Autowired
    UserRestController userRestController;
    Logger log =  LoggerFactory.getLogger(UserTest.class);
    @Test
    void testGetUser(){
        User user = userRestController.findById(3);
        log.info("User : {}",user);
    }
}
