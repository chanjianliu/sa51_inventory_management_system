
package com.team9.motors.service;

import com.team9.motors.model.User;
import com.team9.motors.repository.UserRepository;
import com.team9.motors.springSecurity.MainUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Spring Security interface implemented which expects: UserDetails loadUserByUsername(String username) throws UsernameNotFoundException



@Service
public class LoginService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User does not exist");
            }
        return new MainUser(user);
    }
}

