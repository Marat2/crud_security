package com.crud.boot.controller;

import com.crud.boot.domain.Role;
import com.crud.boot.domain.User;
import com.crud.boot.repository.RoleRepository;
import com.crud.boot.repository.UserRepository;
import com.crud.boot.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {
    @Autowired
    UserService userService;
    RoleRepository roleRepository;
    UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    public UserController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String getIndex(ModelMap model){
        List<User> users = userService.listUsers();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("users",users);
        model.addAttribute("allRoles",roles);
        model.addAttribute("user",new User());
        return "index";
    }
    /*@RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addUser(){
        return "add";
    }*/

    @PostMapping(path = "/save",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String saveUser(User user){
        System.out.println("USER : "+user.getRoles());
        String passwd = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder(12).encode(passwd));
        userService.add(user);
        return "redirect:/admin/all";
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
