package com.fpt.shopping.services;

import com.fpt.shopping.entities.User;
import com.fpt.shopping.repositories.UserRepository;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Optional<User> optionalUser = userRepository.findByName(username);

        User user = null;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), Collections.emptyList());
    }
}
