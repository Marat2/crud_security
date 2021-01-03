package com.crud.boot.controller;


import com.crud.boot.domain.User;
import com.crud.boot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;


@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        System.out.println("1user2 : "+ userService.loadUserByUsername("ADMIN").getAuthorities().toString());
        return "login";
    }
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String userInfo(Principal principal, ModelMap model) {
        //System.out.println("USER :"+principal.getName());
        User user = userService.getByName(principal.getName());
        //System.out.println("YYYY -"+user.toString());
        model.addAttribute("user",user);
        return "user";
    }
}
