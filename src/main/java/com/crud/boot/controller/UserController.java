package com.crud.boot.controller;

import com.crud.boot.domain.User;
import com.crud.boot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;
    Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getIndex(ModelMap model){
        List<User> users = userService.listUsers();
        model.addAttribute("users",users);
        return "index";
    }
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(){
        return "add";
    }
    @PostMapping(path = "/save",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveUser(User user){
        userService.add(user);
        return "redirect:/admin/all";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editUser(@RequestParam Integer id, ModelMap model){
        Optional<User> user = userService.userById(id);
        model.addAttribute("user",user.get());
        return "edit";
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
