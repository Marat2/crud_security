package com.crud.boot.services;


import com.crud.boot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void add(User user);
    void update(Integer id,User user);
    void delete(Integer id);
    List<User> listUsers();
    Optional<User> userById(Integer id);
    UserDetails loadUserByUsername(String s);
    User getByName(String s);
}
