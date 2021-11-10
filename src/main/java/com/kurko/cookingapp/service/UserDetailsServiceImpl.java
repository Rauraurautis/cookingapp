/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kurko.cookingapp.service;

import com.kurko.cookingapp.model.UserDetailsImpl;
import com.kurko.cookingapp.model.User;
import com.kurko.cookingapp.model.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository ur;
    
    public String postNewUser(User user) {
        Optional<User> u = ur.findByUserName(user.getUserName());
        if (u.isPresent()) {
            return "signup";
        }
        
        user.setActive(true);
        user.setRoles("USER");
        ur.save(user);
        return "login"; 
        
    }
    
    public List<User> getUsers() {
        List<User> list = new ArrayList();
        ur.findAll().forEach(user -> list.add(user));
        return list;
    }
    
    
    
    
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = ur.findByUserName(userName);
        
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
        
        return user.map(a -> new UserDetailsImpl(a)).get();
    }
    
}
