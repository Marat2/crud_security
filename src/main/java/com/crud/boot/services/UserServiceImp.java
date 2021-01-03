package com.crud.boot.services;

//import com.crud.boot.domain.Role;
import com.crud.boot.domain.Role;
import com.crud.boot.domain.User;
import com.crud.boot.repository.UserRepository;

//import com.crud.boot.security.SecurityUser;
import com.crud.boot.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

//import java.util.HashSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
//import java.util.Set;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //@Transactional
    public void add(User user) {
        userRepository.save(user);
    }
    //@Transactional
    public void update(Integer id, User user) {
        userRepository.save(user);
    }
    //@Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }



    public Optional<User> userById(Integer id) {
        return userRepository.findById(id);
    }
    //@Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(s);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role:user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return SecurityUser.fromUser(user);
    }

    //@Transactional
    public User getByName(String s){
        User user = userRepository.findByUsername(s);
        return user;
    }
}
