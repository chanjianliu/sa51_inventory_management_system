package com.team9.motors.springSecurity;

import com.team9.motors.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class MainUser implements UserDetails {
    private User user;
    public MainUser(User user){
        super();
        this.user = user;
    }

//methods from UserDetails interface which must be implemented


    //Standard implementation which returns a collection of user authorities which can be used for configuration late
    //it must start with "ROLE_" before adding the actual role: requirement from spring security

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + user.getRole().name();
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

     //not tracking the methods from here on so can hardcode to return true
      //must implement because of UserDetails interface

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
