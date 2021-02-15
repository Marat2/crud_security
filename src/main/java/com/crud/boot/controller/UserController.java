package com.crud.boot.controller;

import com.crud.boot.domain.User;
import com.crud.boot.repository.RoleRepository;
import com.crud.boot.repository.UserRepository;
import com.crud.boot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserController(RoleRepository roleRepository,UserService userService,UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(){
        return "add";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getByName(username);
        model.addAttribute("users",user);
        return "user";
    }

    @PostMapping(path = "/update",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String updateUser(User user){
        userService.update(user.getId(),user);
        return "redirect:/admin/all";
    }
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteUser(@RequestParam Integer id){
        userService.delete(id);
        return "redirect:/admin/all";
    }

}
