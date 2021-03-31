package com.greenfox.greenbay.services;

import com.greenfox.greenbay.models.User;
import com.greenfox.greenbay.repositories.UserRepository;
import com.greenfox.greenbay.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser
            .orElseThrow(() -> new UsernameNotFoundException("No user with username " + username + "has been found."));
        return optionalUser.map(MyUserDetails::new).get();
    }
}
