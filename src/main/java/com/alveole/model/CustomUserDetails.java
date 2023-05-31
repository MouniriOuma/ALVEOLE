package com.alveole.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Return the user's authorities/roles
        // You may need to adjust this based on your application's authorization logic
        return null;
    }

    @Override
    public String getPassword() {
        // Return the user's password
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // Return the user's username
        return user.getUsername();
    }

    // Implement the remaining methods of the UserDetails interface

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Modify this based on your application's logic
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Modify this based on your application's logic
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Modify this based on your application's logic
    }

    @Override
    public boolean isEnabled() {
        return true;  // Modify this based on your application's logic
    }
}
