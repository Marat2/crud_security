package com.crud.boot.services;


import com.crud.boot.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public void add(User user);
    public void update(Integer id,User user);
    public void delete(Integer id);
    public List<User> listUsers();
    public Optional<User> userById(Integer id);
    public UserDetails loadUserByUsername(String s);
    public User getByName(String s);
}
